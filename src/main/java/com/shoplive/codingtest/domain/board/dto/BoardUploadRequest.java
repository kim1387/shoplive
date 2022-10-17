package com.shoplive.codingtest.domain.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class BoardUploadRequest {

  // 게시판 제목
  @Size(max = 50, message = "게시물 제목은 50자까지 가능")
  private String title;

  // 게시글 작성자
  private Long userId;

  // 게시판 내용
  @Size(min = 1, max = 2000, message = "게시물 내용은 2000자까지 가능")
  private String content;

  // 게시물에 함께 등록된 이미지들
  @Size(max = 2, message = "게시물 이미지는 2개까지 추가 가능.")
  private List<MultipartFile> boardImages = new ArrayList<>();
}
