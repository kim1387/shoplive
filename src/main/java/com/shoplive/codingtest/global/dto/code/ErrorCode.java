package com.shoplive.codingtest.global.dto.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  // Global
  INTERNAL_SERVER_ERROR(500, "G001", "서버 오류"),

  // USER
  USER_NOT_FOUND(400, "U001", "유저를 찾을 수없습니다."),
  IMAGE_NOT_UPLOAD_LOCAL(400, "I001","로컬에 이미지 업로드 실패"),
  IMAGE_NOT_DELETE_LOCAL(400, "I002","로컬에 이미지 삭제 실패")
  ;
  private final int status;
  private final String code;
  private final String message;
}
