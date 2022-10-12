package com.shoplive.codingtest.global.dto.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //Global
    INTERNAL_SERVER_ERROR(500, "G001", "서버 오류"),

    // USER
    USER_NOT_FOUND(400, "U001", "유저를 찾을 수없습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
