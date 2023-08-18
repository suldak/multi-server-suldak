package com.sulsul.suldaksuldak.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class Interceptor implements HandlerInterceptor {
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception
    {
        log.info("-------------------------------------------------------");
        log.info("Req URL ==> [" + request.getMethod() + "] " + request.getRequestURI());
        Map<String, String[]> parameterMap = request.getParameterMap();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String parameterName = entry.getKey();
            String[] parameterValues = entry.getValue();
            String parameterValueString = String.join(", ", parameterValues);
            stringBuilder.append(parameterName).append(": ").append(parameterValueString).append("\n");
        }
        log.info(stringBuilder.toString());
        log.info("-------------------------------------------------------");

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
