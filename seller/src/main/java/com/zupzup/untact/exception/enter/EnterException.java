package com.zupzup.untact.exception.enter;

import com.zupzup.untact.exception.BaseException;
import com.zupzup.untact.exception.BaseExceptionType;

public class EnterException extends BaseException {

    private BaseExceptionType exType;

    public EnterException(BaseExceptionType exType) {
        this.exType = exType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exType;
    }
}
