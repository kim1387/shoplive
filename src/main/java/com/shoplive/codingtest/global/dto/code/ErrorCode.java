package com.shoplive.codingtest.global.dto.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  // Global
  INTERNAL_SERVER_ERROR(500, "G001", "서버 오류"),

  // USER
  USER_NOT_FOUND(400, "U001", "유저를 찾을 수 없습니다."),


  // BOARD
  BOARD_NOT_FOUND(400, "B001", "게시물을 찾을 수 없습니다."),
  CANT_UPDATE_OTHERS_BOARD(401,"B002","다른 사람의 게시물을 수정할 수 없습니다."),

  //IMAGE
  IMAGE_NOT_UPLOAD_LOCAL(400, "I001", "로컬에 이미지 업로드 실패"),
  IMAGE_NOT_DELETE_LOCAL(400, "I002", "로컬에 이미지 삭제 실패"),
  IMAGE_NOT_FOUND(400,"I003", "이미지를 찾을 수 없습니다.")
  ;

  private final int status;
  private final String code;
  private final String message;
}
