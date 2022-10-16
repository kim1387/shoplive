package com.shoplive.codingtest.domain.board.controller;

import com.shoplive.codingtest.domain.board.dto.BoardUploadRequest;
import com.shoplive.codingtest.domain.board.dto.BoardUploadResponse;
import com.shoplive.codingtest.domain.board.service.BoardService;
import com.shoplive.codingtest.global.dto.ResultResponse;
import com.shoplive.codingtest.global.dto.code.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/upload")
    public ResponseEntity<ResultResponse> uploadImage(BoardUploadRequest request){

        BoardUploadResponse boardUploadResponse = boardService.upload(request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.BOARD_CREATE_SUCCESS, boardUploadResponse));
    }


}
