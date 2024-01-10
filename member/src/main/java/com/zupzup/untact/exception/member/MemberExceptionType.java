package com.zupzup.untact.exception.member;

import com.zupzup.untact.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements BaseExceptionType {
    // 회원가입, 로그인 시
    ALREADY_EXIST_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 잘못되었습니다."),
    NOT_FOUND_MEMBER(HttpStatus.BAD_REQUEST, "회원 정보가 없습니다."),
    NOT_SAME_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 같지 않습니다."),
    CANNOT_USE_SAME_PASSWORD(HttpStatus.BAD_REQUEST, "이전 비밀번호와 같은 비밀번호를 사용할 수 없습니다."),
    SERVER_ERR(HttpStatus.EXPECTATION_FAILED, "저장에 실패하였습니다. 다시 시도해주세요.");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MemberExceptionType(HttpStatus httpStatus, String errorMessage) {
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
