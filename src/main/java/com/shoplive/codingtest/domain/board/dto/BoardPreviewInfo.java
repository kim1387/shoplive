package com.shoplive.codingtest.domain.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class BoardPreviewInfo {

  // 게시판 제목
  private String title;
  // 게시글 작성자 이름
  private String userName;
  // 게시판 작성 일시
  private LocalDateTime createdDate;
  // 이미지 첨부 여부
  private boolean hasImages;

  @Builder
  @QueryProjection
  public BoardPreviewInfo(
      String title, String userName, LocalDateTime createdDate, boolean hasImages) {
    this.title = title;
    this.userName = userName;
    this.createdDate = createdDate;
    this.hasImages = hasImages;
  }
}
