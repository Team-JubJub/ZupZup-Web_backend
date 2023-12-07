package com.zupzup.untact.exception.apple;

public class AppleException extends Exception {

    private AppleExceptionType exType;

    public AppleException(AppleExceptionType exType) {
        this.exType = exType;
    }

    public AppleExceptionType getExceptionType() {
        return exType;
    }
}

