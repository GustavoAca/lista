package com.glaiss.lista.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class LogginMdcFilter extends OncePerRequestFilter {

    @Value("${spring.application.name:unknown-service}")
    private String appName;

    @Value("${xray.trace.id")
    private String xrayTraceId;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            MDC.put("apiName", appName);
            MDC.put("traceId", xrayTraceId);

            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
