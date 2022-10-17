package com.shoplive.codingtest.domain.image.exception;

import com.shoplive.codingtest.global.dto.code.ErrorCode;
import com.shoplive.codingtest.global.exception.BusinessException;

public class ImageNotFoundException extends BusinessException {

  public ImageNotFoundException() {
    super(ErrorCode.IMAGE_NOT_FOUND);
  }
}
