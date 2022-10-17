package com.shoplive.codingtest.domain.board.controller;

import com.shoplive.codingtest.domain.board.dto.BoardDetailResponse;
import com.shoplive.codingtest.domain.board.dto.BoardUpdateRequest;
import com.shoplive.codingtest.domain.board.dto.BoardUploadRequest;
import com.shoplive.codingtest.domain.board.service.BoardService;
import com.shoplive.codingtest.global.dto.ResultResponse;
import com.shoplive.codingtest.global.dto.code.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@RestController
public class BoardController {

  private final BoardService boardService;

  @PostMapping("/upload")
  public ResponseEntity<ResultResponse> uploadImage(@Valid BoardUploadRequest request) {

    BoardDetailResponse boardDetailResponse = boardService.upload(request);
    return ResponseEntity.ok(
        ResultResponse.of(ResultCode.BOARD_CREATE_SUCCESS, boardDetailResponse));
  }

  @GetMapping("/{boardId}")
  public ResponseEntity<ResultResponse> getBoardDetail(@PathVariable Long boardId) {
    BoardDetailResponse response = boardService.getBoardDetail(boardId);

    return ResponseEntity.ok(ResultResponse.of(ResultCode.BOARD_DETAIL_GET_SUCCESS, response));
  }

  @PutMapping
  public ResponseEntity<ResultResponse> updateBoardDetail(@Valid BoardUpdateRequest request) {
    BoardDetailResponse response = boardService.updateBoard(request);

    return ResponseEntity.ok(ResultResponse.of(ResultCode.BOARD_DETAIL_UPDATE_SUCCESS, response));
  }
}
