package com.zupzup.untact.exception.enter;

import com.zupzup.untact.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum EnterExceptionType implements BaseExceptionType {

    ;

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    EnterExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrMsg() {
        return this.errorMessage;
    }
}
