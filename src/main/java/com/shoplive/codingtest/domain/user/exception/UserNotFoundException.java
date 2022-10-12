package com.shoplive.codingtest.domain.user.exception;

import com.shoplive.codingtest.global.dto.code.ErrorCode;
import com.shoplive.codingtest.global.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
  public UserNotFoundException() {
    super(ErrorCode.USER_NOT_FOUND);
  }
}
