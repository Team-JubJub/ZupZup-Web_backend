package com.zupzup.untact.exception;

public class MemberException extends BaseException{

    private BaseExceptionType exType;

    public MemberException(BaseExceptionType exType) {
        this.exType = exType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exType;
    }
}
