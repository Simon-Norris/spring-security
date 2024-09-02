package com.learn.spring_security.config.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppCorsFilter implements Filter {
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    private static final Logger logger = LoggerFactory.getLogger(AppCorsFilter.class);

    @Value("${allow.client.url.list}")
    private String clientAppUrl;

    private List<String> allowedOrigins;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (clientAppUrl != null && !clientAppUrl.trim().isEmpty()) {
            allowedOrigins = Arrays.asList(clientAppUrl.split(","));
        } else {
            allowedOrigins = new ArrayList<>();
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        logger.info("Request Method: {}, URI: {}", request.getMethod(), request.getRequestURI());
        setCorsHeaders(response, request);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            logger.debug("Handling OPTIONS request for CORS preflight check");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    private void setCorsHeaders(HttpServletResponse response, HttpServletRequest request) {
        String originHeader = request.getHeader("Origin");
        if (allowedOrigins.contains(originHeader)) response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, originHeader);
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "*");
    }

    @Override
    public void destroy() { }

}
