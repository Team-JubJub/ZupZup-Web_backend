package com.zupzup.untact.exception.apple;

public enum AppleExceptionType {

    NO_MEMBER("회원 정보가 존재하지 않습니다."),
    NO_STORE("관련된 가게가 존재하지 않습니다."),
    ALREADY_WANTED_DELETE("이미 탈퇴를 신청한 회원입니다.");

    private String errMsg;

    AppleExceptionType(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
