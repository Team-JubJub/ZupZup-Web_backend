package com.zupzup.untact.exception;

public class CustomException extends RuntimeException {

    private CustomErrorCode customErrorCode;

    public CustomException(CustomErrorCode customErrorCode) {
        super(customErrorCode.getMessage());
    }
}
