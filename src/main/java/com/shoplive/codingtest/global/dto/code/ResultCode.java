package com.shoplive.codingtest.global.dto.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

  // user
  USER_CREATE_SUCCESS("U001", "사용자 생성 성공"),


  // board
  BOARD_CREATE_SUCCESS("B001", "게시물 업로드 성공"),
  BOARD_DETAIL_GET_SUCCESS("B002", "게시물 상세 정보 조회 성공"),
  ;

  private final String code;
  private final String message;
}
