package com.cashrich.crypto.service;


import com.cashrich.crypto.constants.ResponseMessages;
import com.cashrich.crypto.dto.response.CryptoResponse;
import com.cashrich.crypto.entity.ExternalApiTrack;
import com.cashrich.crypto.entity.User;
import com.cashrich.crypto.exceptions.DataValidationException;
import com.cashrich.crypto.repository.ExternalApiTrackRepository;
import com.cashrich.crypto.repository.UserRepository;
import com.cashrich.crypto.utility.jwt.CommonUtils;
import com.cashrich.crypto.wrapper.ResponseEntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@Log4j2
public class ThirdPartyService {

    @Autowired
    private UserRepository userRepository;

    @Value("${crypto.endpoint}")
    private String endpoint;

    @Value("${crypto.key}")
    private String key;

    @Autowired
    private ExternalApiTrackService externalApiTrackService;

    public ResponseEntity<?> fetchData(UserDetails userDetails,String symbol) {

        if(symbol != null && symbol.isEmpty()){
            throw new DataValidationException(ResponseMessages.INVALID_SYMBOL);
        }

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", key);
        headers.set("USER_ID",userDetails.getUsername());
        RestTemplate restTemplate = new RestTemplate();

        log.info("Third Party service : starting request for endpoint :{}{}", endpoint, symbol);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String thirdPartyResponse ="";
        long start = System.currentTimeMillis();

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(endpoint + symbol, HttpMethod.GET, entity, String.class);
            thirdPartyResponse = responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                log.error("Unauthorized error while fetching data from third-party API: {}", e.getMessage());
                return ResponseEntityWrapper.failureResponseBuilder("Unauthorized access to third-party API. Please check your API key.", null,HttpStatus.UNAUTHORIZED);
            } else {
                log.error("HTTP client error while fetching data from third-party API: {}", e.getStatusCode());
                return ResponseEntityWrapper.failureResponseBuilder("Third-party API returned an error: " + e.getMessage() , null,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (HttpServerErrorException e) {
            log.error("HTTP server error while fetching data from third-party API: {}", e.getStatusCode());
            return ResponseEntityWrapper.failureResponseBuilder("Third-party API returned an error: " + e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResourceAccessException e) {
            log.error("Resource access error while fetching data from third-party API: {}", e.getMessage());
            return ResponseEntityWrapper.failureResponseBuilder("Failed to access third-party API: " + e.getMessage(),null, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            log.error("An error occurred while fetching data from third-party API: {}", e.getMessage());
            return ResponseEntityWrapper.failureResponseBuilder("An unexpected error occurred: " + e.getMessage(),null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ExternalApiTrack externalApiTrack = new ExternalApiTrack();
        externalApiTrack.setApiUrl(endpoint+symbol);
        externalApiTrack.setRequest(symbol);
        externalApiTrack.setReqCompleteTime(System.currentTimeMillis()-start);
        externalApiTrack.setResponse(thirdPartyResponse);

        log.info("Third Party service : response received for endpoint : {}{} :: {}", endpoint, symbol, thirdPartyResponse);
        externalApiTrackService.save(externalApiTrack);

        CryptoResponse cryptoResponse = CommonUtils.extractObjectFromString(thirdPartyResponse, CryptoResponse.class);
        if(cryptoResponse != null && cryptoResponse.getStatus() != null && cryptoResponse.getStatus().getError_code()!=null && !cryptoResponse.getStatus().getError_code().equalsIgnoreCase("0")){
            // case for api failure
            return ResponseEntityWrapper.failureResponseBuilder(cryptoResponse.getStatus().getError_message(), cryptoResponse.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntityWrapper.successResponseBuilder(ResponseMessages.API_SUCCESS,cryptoResponse);
    }
}
