package com.learn.spring_security.base;

import com.learn.spring_security.app.exceptions.RoleNotFoundException;
import com.learn.spring_security.app.exceptions.UsernameNotFoundException;
import com.learn.spring_security.utils.ApiResponse;
import com.learn.spring_security.utils.ApiResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiResponseModel> handleAccessDeniedException(AccessDeniedException ex) {
        return ApiResponse.errorWithStatusAndMessage(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponseModel> handleExceptions(Exception ex) {
        return ApiResponse.errorWithStatusAndMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiResponseModel> handleAuthExceptions(AuthenticationException ex) {
        if (ex instanceof BadCredentialsException) {
            return ApiResponse.errorWithStatusAndMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ApiResponse.errorWithStatusAndMessage(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseModel> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ApiResponse.errorWithStatusAndMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseModel> handleRoleNotFoundException(RoleNotFoundException ex) {
        return ApiResponse.errorWithStatusAndMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseModel> handleBadCredentialsExceptions(BadCredentialsException ex) {
        return ApiResponse.errorWithStatusAndMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseModel> handleBadCredentialsExceptions(SQLException ex) {
        return ApiResponse.errorWithStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
