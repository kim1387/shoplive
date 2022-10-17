package com.shoplive.codingtest.domain.board.controller;

import com.shoplive.codingtest.domain.board.dto.BoardDetailResponse;
import com.shoplive.codingtest.domain.board.dto.BoardListResponse;
import com.shoplive.codingtest.domain.board.dto.BoardUpdateRequest;
import com.shoplive.codingtest.domain.board.dto.BoardUploadRequest;
import com.shoplive.codingtest.domain.board.service.BoardService;
import com.shoplive.codingtest.global.dto.ResultResponse;
import com.shoplive.codingtest.global.dto.code.ResultCode;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@RestController
public class BoardController {

  private final BoardService boardService;

  @PostMapping("/upload")
  public ResponseEntity<ResultResponse> uploadImage(
      @Valid @ModelAttribute BoardUploadRequest request) {

    BoardDetailResponse boardDetailResponse = boardService.upload(request);
    return ResponseEntity.ok(
        ResultResponse.of(ResultCode.BOARD_CREATE_SUCCESS, boardDetailResponse));
  }

  @GetMapping("/{boardId}")
  public ResponseEntity<ResultResponse> getBoardDetail(@PathVariable Long boardId) {
    BoardDetailResponse response = boardService.getBoardDetail(boardId);

    return ResponseEntity.ok(ResultResponse.of(ResultCode.BOARD_DETAIL_GET_SUCCESS, response));
  }

  @GetMapping("/list/{page}")
  public ResponseEntity<ResultResponse> getBoardListPage(
      @PathVariable int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(defaultValue = "false", required = false) boolean isTimeReversed) {
    BoardListResponse response = boardService.getBoardListPage(page, size, isTimeReversed);

    return ResponseEntity.ok(ResultResponse.of(ResultCode.BOARD_LIST_PAGE_GET_SUCCESS, response));
  }

  @GetMapping("/search/{page}")
  public ResponseEntity<ResultResponse> searchBoardWithTitleOrContent(
      @PathVariable int page,
      @RequestParam(required = false) int size,
      @RequestParam(defaultValue = "false", required = false) boolean isTimeReversed,
      @RequestParam String search) {
    BoardListResponse response =
        boardService.searchBoardWithTitleOrContent(page, size, isTimeReversed, search);

    return ResponseEntity.ok(
        ResultResponse.of(ResultCode.BOARD_LIST_PAGE_SEARCH_SUCCESS, response));
  }

  @PutMapping
  public ResponseEntity<ResultResponse> updateBoardDetail(@Valid BoardUpdateRequest request) {
    BoardDetailResponse response = boardService.updateBoard(request);

    return ResponseEntity.ok(ResultResponse.of(ResultCode.BOARD_DETAIL_UPDATE_SUCCESS, response));
  }

  @DeleteMapping("/{boardId}")
  public ResponseEntity<ResultResponse> deleteBoard(
      @PathVariable Long boardId, @RequestParam Long userId) {
    boardService.deleteBoard(boardId, userId);

    return ResponseEntity.ok(ResultResponse.of(ResultCode.BOARD_DELETE_SUCCESS, new Object()));
  }
}
