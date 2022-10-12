package com.shoplive.codingtest.global.exception.handler;

import com.shoplive.codingtest.global.dto.ErrorResponse;
import com.shoplive.codingtest.global.exception.BusinessException;
import com.shoplive.codingtest.global.dto.code.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler
  protected ResponseEntity<ErrorResponse> handleRuntimeException(BusinessException e) {
    final ErrorCode errorCode = e.getErrorCode();
    final ErrorResponse response =
        ErrorResponse.builder()
            .errorMessage(errorCode.getMessage())
            .businessCode(errorCode.getCode())
            .build();
    log.warn(e.getMessage());
    return ResponseEntity.status(errorCode.getStatus()).body(response);
  }
}
