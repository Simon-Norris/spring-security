package com.learn.spring_security.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.spring_security.utils.ApiResponse;
import com.learn.spring_security.utils.ApiResponseModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        logger.error("::: UNAUTHORIZED ERROR: {} :::", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Exception exception = (Exception) request.getAttribute("exception");
        final ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<ApiResponseModel> apiResponseModelResponseEntity;
        apiResponseModelResponseEntity = ApiResponse.errorWithStatusAndMessage(HttpStatus.UNAUTHORIZED, Objects.requireNonNullElse(exception, authException).getMessage());
        mapper.writeValue(response.getOutputStream(), apiResponseModelResponseEntity.getBody());
    }

}
