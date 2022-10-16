package com.shoplive.codingtest.domain.image.exception;

import com.shoplive.codingtest.global.dto.code.ErrorCode;
import com.shoplive.codingtest.global.exception.BusinessException;

public class FailLocalFileDeleted extends BusinessException {
  public FailLocalFileDeleted() {
    super(ErrorCode.IMAGE_NOT_DELETE_LOCAL);
  }
}
