package com.shoplive.codingtest.domain.image.exception;

import com.shoplive.codingtest.global.dto.code.ErrorCode;
import com.shoplive.codingtest.global.exception.BusinessException;

public class FailedUploadImageToLocalException extends BusinessException {
  public FailedUploadImageToLocalException() {
    super(ErrorCode.IMAGE_NOT_UPLOAD_LOCAL);
  }
}
