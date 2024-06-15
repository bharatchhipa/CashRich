package com.cashrich.crypto.config;

import com.cashrich.crypto.interceptor.AuthInterceptor;
import com.cashrich.crypto.interceptor.AuthInterceptorHelper;
import com.cashrich.crypto.service.ApiReqRespService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ApiReqRespService apiReqRespService;
    private final AuthInterceptorHelper authInterceptorHelper;

    public WebConfig(ApiReqRespService apiReqRespService, AuthInterceptorHelper authInterceptorHelper) {
        this.apiReqRespService = apiReqRespService;
        this.authInterceptorHelper = authInterceptorHelper;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(apiReqRespService, authInterceptorHelper)).addPathPatterns("/**");
    }
}