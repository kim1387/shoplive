package com.shoplive.codingtest.domain.board.exception;

import com.shoplive.codingtest.global.dto.code.ErrorCode;
import com.shoplive.codingtest.global.exception.BusinessException;

public class CantUpdateOthersBoard extends BusinessException {
  public CantUpdateOthersBoard() {
    super(ErrorCode.CANT_UPDATE_OTHERS_BOARD);
  }
}
