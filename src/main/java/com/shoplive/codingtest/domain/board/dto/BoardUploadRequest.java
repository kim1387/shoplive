package com.shoplive.codingtest.domain.board.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardUploadRequest {
    // 게시판 제목
    private String title;

    // 게시글 작성자
    private Long userId;

    // 게시판 내용
    private String content;

    // 게시물에 함께 등록된 이미지들
    private List<MultipartFile> boardImages = new ArrayList<>();
}
