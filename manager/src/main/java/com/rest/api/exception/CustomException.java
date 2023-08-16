package com.rest.api.exception;

public class CustomException extends RuntimeException {

    private CustomErrorCode customErrorCode;

    public CustomException(CustomErrorCode customErrorCode) {
        super(customErrorCode.getMessage());
    }
}
