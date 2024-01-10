package com.zupzup.untact.exception;

import org.springframework.http.HttpStatus;

public enum ManagerExceptionType implements BaseExceptionType {

    MANAGER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_SAME(HttpStatus.BAD_REQUEST, "비밀번호가 같지 않습니다."),
    EMPTY_LIST(HttpStatus.BAD_REQUEST, "관련된 매장 정보를 찾을 수 없습니다.");

//    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    ManagerExceptionType(HttpStatus httpStatus, String errorMessage) {
//        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

//    @Override
//    public int getErrCode() {
//        return this.errorCode;
//    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrMsg() {
        return this.errorMessage;
    }
}
