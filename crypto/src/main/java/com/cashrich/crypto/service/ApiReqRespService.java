package com.cashrich.crypto.service;

import com.cashrich.crypto.dto.response.ResponseWrapper;
import com.cashrich.crypto.entity.ApiReqResp;
import com.cashrich.crypto.repository.ApiReqRespRespository;
import com.cashrich.crypto.utility.jwt.CommonUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Log4j2
@RequiredArgsConstructor
public class ApiReqRespService {

    private final ApiReqRespRespository apiReqRespRespository;


    public void insertApiReqResponse(HttpServletRequest request, HttpServletResponse response, Long reqCompleteTime) {
        try {
            String requestBody = (String) request.getAttribute("body");
            ResponseWrapper responseWrapper = (ResponseWrapper) request.getAttribute("response");

            ApiReqResp apiReqResp = new ApiReqResp();
            apiReqResp.setApiUrl(request.getRequestURI());
            apiReqResp.setReqCompleteTime(reqCompleteTime);
            apiReqResp.setRequest(requestBody);

            apiReqResp.setIpAddress(CommonUtils.getClientIp(request));
            apiReqResp.setApiMethod(request.getMethod());
            apiReqResp.setApiType(null);
            apiReqResp.setResponseEntityCode(response.getStatus());

            if (responseWrapper != null) {
                apiReqResp.setResponse(new Gson().toJson(responseWrapper));
            }
            if (responseWrapper != null && responseWrapper.getStatus() != null) {
                apiReqResp.setResponseStatus(responseWrapper.getStatus());
            }
            if (responseWrapper != null && responseWrapper.getCode() != null) {
                apiReqResp.setResponseCode(responseWrapper.getCode());
            }


            apiReqRespRespository.save(apiReqResp);
        } catch (Exception ex) {
            log.error("An Error Occurred::", ex);
        }
    }

}

