package com.learn.spring_security.utils;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {
    public static ResponseEntity<ApiResponseModel> successWithDataOnly(@NotNull Object data) {
        return ResponseEntity.ok(new ApiResponseModel(HttpStatus.OK.value(), "Success", data));
    }

    public static ResponseEntity<ApiResponseModel> success(@NotNull HttpStatus status, @NotNull String message, @NotNull Object data) {
        return ResponseEntity.status(status).body(new ApiResponseModel(status.value(), message, data));
    }

    public static ResponseEntity<ApiResponseModel> successWithStatusAndReason(@NotNull HttpStatus status, @NotNull String message) {
        return ResponseEntity.status(status).body(new ApiResponseModel(status.value(), message, null));
    }

    public static ResponseEntity<ApiResponseModel> error(@NotNull HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponseModel(status.value(), "An unexpected error occurred", null));
    }

    public static ResponseEntity<ApiResponseModel> errorWithStatusAndMessage(@NotNull HttpStatus status, @NotNull String message) {
        return ResponseEntity.status(status).body(new ApiResponseModel(status.value(), message, null));
    }

    public static ResponseEntity<ApiResponseModel> badRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseModel(HttpStatus.BAD_REQUEST.value(), "Bad Request", null));
    }

    public static ResponseEntity<ApiResponseModel> badRequestWithReason(@NotNull String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseModel(HttpStatus.BAD_REQUEST.value(), message, null));
    }
}
