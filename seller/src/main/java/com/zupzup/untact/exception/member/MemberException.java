package com.zupzup.untact.exception.member;

import com.zupzup.untact.exception.BaseException;
import com.zupzup.untact.exception.BaseExceptionType;

public class MemberException extends BaseException {

    private BaseExceptionType exType;

    public MemberException(BaseExceptionType exType) {
        this.exType = exType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exType;
    }
}
