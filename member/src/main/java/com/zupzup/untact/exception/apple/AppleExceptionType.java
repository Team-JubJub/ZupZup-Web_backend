package com.zupzup.untact.exception.apple;

public enum AppleExceptionType {

    NO_MEMBER("회원 정보가 존재하지 않습니다.");

    private String errMsg;

    AppleExceptionType(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
