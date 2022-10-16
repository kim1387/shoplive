package com.shoplive.codingtest.domain.board.service;

import com.shoplive.codingtest.domain.board.domain.entity.Board;
import com.shoplive.codingtest.domain.board.domain.repository.BoardRepository;
import com.shoplive.codingtest.domain.board.dto.BoardUploadRequest;
import com.shoplive.codingtest.domain.board.dto.BoardDetailResponse;
import com.shoplive.codingtest.domain.board.exception.BoardNotFoundException;
import com.shoplive.codingtest.domain.image.domain.entity.Image;
import com.shoplive.codingtest.domain.image.service.ImageService;
import com.shoplive.codingtest.domain.user.domain.entity.User;
import com.shoplive.codingtest.domain.user.domain.repository.UserRepository;
import com.shoplive.codingtest.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

  private final BoardRepository boardRepository;

  private final UserRepository userRepository;

  private final ImageService imageService;

  @Transactional
  public BoardDetailResponse upload(BoardUploadRequest boardUploadRequest) {

    final User loggedInUser = loadUserInfoWithUserId(boardUploadRequest);
    final Board newBoard =
        Board.builder()
            .user(loggedInUser)
            .title(boardUploadRequest.getTitle())
            .content(boardUploadRequest.getContent())
            .build();

    boardRepository.save(newBoard);

    final List<Image> uploadedImageList =
        boardUploadRequest.getBoardImages().stream()
            .map(imageService::uploadToS3)
            .peek(image -> image.setBoard(newBoard))
            .collect(Collectors.toList());

    // todo 성능 개선 하기
    // 1. native 쿼리
    // 2. batch size
    // 3. rewriteBatchedStatements
    final List<String> imageCloudPathList =
        imageService.saveAllImage(uploadedImageList).stream()
            .map(Image::getCloudPath)
            .collect(Collectors.toList());

    return BoardDetailResponse.builder()
        .userName(loggedInUser.getName())
        .createdDate(newBoard.getCreatedDate())
        .updatedDate(newBoard.getUpdatedDate())
        .title(newBoard.getTitle())
        .content(newBoard.getContent())
        .boardImages(imageCloudPathList)
        .build();
  }

  // TODO N+1 이슈 발생 하지만 이미지가 2개까지 가능해 큰 문제는 아님
  public BoardDetailResponse getBoardDetail(Long boardId) {
    Board board = boardRepository.findByIdAndRemoved(boardId).orElseThrow(BoardNotFoundException::new);
    return BoardDetailResponse.builder()
        .title(board.getTitle())
        .content(board.getContent())
        .boardImages(
            board.getBoardImages().stream().map(Image::getCloudPath).collect(Collectors.toList()))
        .createdDate(board.getCreatedDate())
        .updatedDate(board.getUpdatedDate())
        .build();
  }

  private User loadUserInfoWithUserId(BoardUploadRequest boardUploadRequest) {
    final Long loggedInUserId = boardUploadRequest.getUserId();
    return userRepository.findById(loggedInUserId).orElseThrow(UserNotFoundException::new);
  }
}
