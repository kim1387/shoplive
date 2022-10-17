package com.shoplive.codingtest.domain.board.exception;

import com.shoplive.codingtest.global.dto.code.ErrorCode;
import com.shoplive.codingtest.global.exception.BusinessException;

public class CantDeleteAlreadyBoard extends BusinessException {
    public CantDeleteAlreadyBoard() {
        super(ErrorCode.CANT_DELETE_ALREADY_BOARD);
    }
}
