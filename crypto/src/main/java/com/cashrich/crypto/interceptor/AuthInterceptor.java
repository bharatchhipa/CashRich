package com.cashrich.crypto.interceptor;

import com.cashrich.crypto.service.ApiReqRespService;
import com.cashrich.crypto.utility.jwt.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthInterceptor implements HandlerInterceptor {

    private final ApiReqRespService apiReqRespService;
    private final AuthInterceptorHelper authInterceptorHelper;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) {
        long startTime = (Long) request.getAttribute("startTime");
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Ends :: Request URI::" + request.getRequestURI() + ":: total Time Taken in ms=" + timeTaken);
        if (request.getMethod() != null &&
                !(request.getRequestURI().equalsIgnoreCase("/initializeToken"))) {
            apiReqRespService.insertApiReqResponse(request, response, timeTaken);
        }
        MDC.remove("ip");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model) {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        MDC.put("ip", CommonUtils.getClientIp(request));
        log.info("Starts :: Request URI::" + request.getRequestURI());
        authInterceptorHelper.addRequestParameterIfAny(request);
        return true;
    }
}
