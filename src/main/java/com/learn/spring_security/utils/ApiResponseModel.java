package com.learn.spring_security.utils;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponseModel {

    private int status;
    private String message;
    private Object data;

    @Override
    public String toString() {
        return "ApiResponseModel{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
