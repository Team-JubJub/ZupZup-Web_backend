package com.zupzup.untact.exception;

public class LoginException extends BaseException{

    private BaseExceptionType exType;

    public LoginException(BaseExceptionType exType) {
        this.exType = exType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exType;
    }
}
