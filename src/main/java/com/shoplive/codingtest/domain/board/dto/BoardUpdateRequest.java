package com.shoplive.codingtest.domain.board.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class BoardUpdateRequest {

  private Long boardId;
  // 게시판 제목
  @Size(max = 50, message = "게시물 제목은 50자까지 가능")
  private String title;

  // 게시글 수정자
  private Long userId;

  // 게시판 내용
  @Size(min = 1, max = 2000, message = "게시물 내용은 2000자까지 가능")
  private String content;

  // 게시물에 함께 등록된 이미지들
  @Size(max = 2, message = "게시물 이미지는 2개까지 추가 가능.")
  private List<MultipartFile> boardImages = new ArrayList<>();
}
