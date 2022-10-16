package com.shoplive.codingtest.domain.board.exception;

import com.shoplive.codingtest.global.dto.code.ErrorCode;
import com.shoplive.codingtest.global.exception.BusinessException;
import lombok.Getter;

@Getter
public class BoardNotFoundException extends BusinessException {

    public BoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND);
    }
}
