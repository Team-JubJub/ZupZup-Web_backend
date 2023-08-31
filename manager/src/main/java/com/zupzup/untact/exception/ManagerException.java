package com.zupzup.untact.exception;

public class ManagerException extends BaseException{

    private BaseExceptionType exType;

    public ManagerException(BaseExceptionType exType) {
        this.exType = exType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exType;
    }
}
