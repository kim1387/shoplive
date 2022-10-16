package com.shoplive.codingtest.domain.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailResponse {

  // 게시판 제목
  private String title;
  // 게시글 작성자 이름
  private String userName;
  // 게시판 내용
  private String content;
  // 게시판 작성 일시
  private LocalDateTime createdDate;
  // 게시판 수정 일시
  private LocalDateTime updatedDate;
  // 게시물에 함께 등록된 이미지 주소들
  private List<String> boardImages = new ArrayList<>();

  @QueryProjection
  public BoardDetailResponse(
      String title,
      String userName,
      String content,
      LocalDateTime createdDate,
      LocalDateTime updatedDate) {
    this.title = title;
    this.userName = userName;
    this.content = content;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }
}
