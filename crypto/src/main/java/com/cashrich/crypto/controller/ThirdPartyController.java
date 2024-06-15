package com.cashrich.crypto.controller;

import com.cashrich.crypto.service.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequestMapping("/api/external")
public class ThirdPartyController {

    @Autowired
    private ThirdPartyService thirdPartyService;

    @GetMapping("/fetch-data/{symbol}")
    public ResponseEntity<?> fetchData(@AuthenticationPrincipal UserDetails userDetails,@PathVariable String symbol) {
        return thirdPartyService.fetchData(userDetails,symbol);
    }
}

