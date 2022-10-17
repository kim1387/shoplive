package com.shoplive.codingtest.domain.board.service;

import com.shoplive.codingtest.domain.board.domain.entity.Board;
import com.shoplive.codingtest.domain.board.domain.repository.BoardRepository;
import com.shoplive.codingtest.domain.board.dto.BoardDetailResponse;
import com.shoplive.codingtest.domain.board.dto.BoardUpdateRequest;
import com.shoplive.codingtest.domain.board.dto.BoardUploadRequest;
import com.shoplive.codingtest.domain.board.exception.BoardNotFoundException;
import com.shoplive.codingtest.domain.board.exception.CantUpdateOthersBoard;
import com.shoplive.codingtest.domain.image.domain.entity.Image;
import com.shoplive.codingtest.domain.image.service.ImageService;
import com.shoplive.codingtest.domain.user.domain.entity.User;
import com.shoplive.codingtest.domain.user.domain.repository.UserRepository;
import com.shoplive.codingtest.domain.user.exception.UserNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class BoardService {

  private final BoardRepository boardRepository;

  private final UserRepository userRepository;

  private final ImageService imageService;

  @Transactional
  public BoardDetailResponse upload(BoardUploadRequest boardUploadRequest) {

    final User loggedInUser = loadUserInfoWithUserId(boardUploadRequest.getUserId());
    final Board newBoard =
        Board.builder()
            .user(loggedInUser)
            .title(boardUploadRequest.getTitle())
            .content(boardUploadRequest.getContent())
            .build();

    boardRepository.save(newBoard);

    List<String> imageCloudPathList =
        UploadImageS3andSaveEntity(newBoard, boardUploadRequest.getBoardImages());

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
    Board board =
        boardRepository.findByIdAndRemoved(boardId).orElseThrow(BoardNotFoundException::new);
    return BoardDetailResponse.builder()
        .title(board.getTitle())
        .content(board.getContent())
        .boardImages(
            board.getBoardImages().stream().map(Image::getCloudPath).collect(Collectors.toList()))
        .createdDate(board.getCreatedDate())
        .updatedDate(board.getUpdatedDate())
        .build();
  }

  @Transactional
  public BoardDetailResponse updateBoard(BoardUpdateRequest boardUpdateRequest) {
    Long boardId = boardUpdateRequest.getBoardId();
    User loggedInUser = loadUserInfoWithUserId(boardUpdateRequest.getUserId());
    Board board =
        boardRepository.findByIdAndRemoved(boardId).orElseThrow(BoardNotFoundException::new);
    checkIsWriter(boardUpdateRequest, board);

    List<Image> imageList = imageService.findImageByBoardId(boardId);
    BoardDetailResponse response =
        BoardDetailResponse.builder()
            .userName(loggedInUser.getName())
            .title(board.getTitle())
            .content(board.getContent())
            .boardImages(
                board.getBoardImages().stream()
                    .map(Image::getCloudPath)
                    .collect(Collectors.toList()))
            .createdDate(board.getCreatedDate())
            .updatedDate(board.getUpdatedDate())
            .build();
    if (!imageList.equals(boardUpdateRequest.getBoardImages())) {
      List<String> imageCloudPathList =
          UploadImageS3andSaveEntity(board, boardUpdateRequest.getBoardImages());
      response.setBoardImages(imageCloudPathList);
    }
    return response;
  }

  private List<String> UploadImageS3andSaveEntity(Board board, List<MultipartFile> boardImages) {
    final List<Image> updatedImageList =
        boardImages.stream()
            .map(imageService::uploadToS3)
            .peek(image -> image.setBoard(board))
            .collect(Collectors.toList());
    return imageService.saveAllImage(updatedImageList).stream()
        .map(Image::getCloudPath)
        .collect(Collectors.toList());
  }

  private void checkIsWriter(BoardUpdateRequest boardUpdateRequest, Board board) {
    if (!Objects.equals(boardUpdateRequest.getUserId(), board.getUser().getId())) {
      throw new CantUpdateOthersBoard();
    }
    board.updateBoardDetail(boardUpdateRequest);
  }

  private User loadUserInfoWithUserId(Long userId) {
    return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
  }
}
