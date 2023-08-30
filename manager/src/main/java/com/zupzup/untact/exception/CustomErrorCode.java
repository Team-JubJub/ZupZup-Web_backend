package com.zupzup.untact.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CustomErrorCode {

    MANAGER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_SAME("비밀번호가 같지 않습니다.");

    private final String message;

    CustomErrorCode( final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
