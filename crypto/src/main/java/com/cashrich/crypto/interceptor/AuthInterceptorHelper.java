package com.cashrich.crypto.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class AuthInterceptorHelper {

    public void addRequestParameterIfAny(HttpServletRequest request) {
        Map<String, String> parameters = getParameters(request);
        if (!parameters.isEmpty()) {
            StringBuilder reqMessage = new StringBuilder();
            reqMessage.append("method = [").append(request.getMethod()).append("]");
            reqMessage.append(" path = [").append(request.getRequestURI()).append("] ");
            StringBuilder reqBody = new StringBuilder().append("parameters = [").append(parameters).append("]");
            reqMessage.append(reqBody);
            log.info("REQUEST: {}", reqMessage);
            request.setAttribute("body", reqBody.toString());
        }
    }

    private Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }
        return parameters;
    }
}
