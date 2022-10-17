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
  BOARD_DETAIL_UPDATE_SUCCESS("B003", "게시물 수정 성공"),
  BOARD_DELETE_SUCCESS("B004", "게시물 삭제 성공"),
  BOARD_LIST_PAGE_GET_SUCCESS("B005", "게시물 리스트 페이징 조회 성공"),
  BOARD_LIST_PAGE_SEARCH_SUCCESS("B006", "게시물 검색 성공"),
  ;

  private final String code;
  private final String message;
}
