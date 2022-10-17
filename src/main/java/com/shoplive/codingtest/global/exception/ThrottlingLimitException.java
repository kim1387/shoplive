package com.shoplive.codingtest.global.exception;

import com.shoplive.codingtest.global.dto.code.ErrorCode;

public class ThrottlingLimitException extends BusinessException{
    public ThrottlingLimitException(ErrorCode errorCode) {
        super(errorCode);
    }
}
