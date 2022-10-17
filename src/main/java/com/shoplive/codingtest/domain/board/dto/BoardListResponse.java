package com.shoplive.codingtest.domain.board.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardListResponse {

  // 게시판 프리뷰
  private List<BoardPreviewInfo> boardPreviewInfoList = new ArrayList<>();
}
