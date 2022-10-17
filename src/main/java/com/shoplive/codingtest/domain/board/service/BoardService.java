package com.shoplive.codingtest.domain.board.service;

import com.shoplive.codingtest.domain.board.domain.entity.Board;
import com.shoplive.codingtest.domain.board.domain.repository.BoardRepository;
import com.shoplive.codingtest.domain.board.dto.*;
import com.shoplive.codingtest.domain.board.exception.BoardNotFoundException;
import com.shoplive.codingtest.domain.board.exception.CantUpdateOthersBoard;
import com.shoplive.codingtest.domain.image.domain.entity.Image;
import com.shoplive.codingtest.domain.image.service.ImageService;
import com.shoplive.codingtest.domain.user.domain.entity.User;
import com.shoplive.codingtest.domain.user.domain.repository.UserRepository;
import com.shoplive.codingtest.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
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
        uploadImageS3andSaveEntity(newBoard, boardUploadRequest.getBoardImages()).stream()
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

  public BoardListResponse getBoardListPage(int page, int size, boolean isTimeReversed) {
    final Pageable pageable = PageRequest.of(page, size);
    List<BoardPreviewInfo> boardPreviewInfoList =
        boardRepository.findBoardPreviewInfoListPage(pageable, isTimeReversed);

    return new BoardListResponse(boardPreviewInfoList);
  }

  public BoardListResponse searchBoardWithTitleOrContent(
      int page, int size, boolean isTimeReversed, String keyword) {
    final Pageable pageable = PageRequest.of(page, size);
    List<BoardPreviewInfo> boardPreviewInfoList =
        boardRepository.getBoardListSearchTitleOrContent(pageable, isTimeReversed, keyword);

    return new BoardListResponse(boardPreviewInfoList);
  }

  @Transactional
  public BoardDetailResponse updateBoard(BoardUpdateRequest boardUpdateRequest) {
    Long boardId = boardUpdateRequest.getBoardId();
    User loggedInUser = loadUserInfoWithUserId(boardUpdateRequest.getUserId());
    Board board =
        boardRepository.findByIdAndRemoved(boardId).orElseThrow(BoardNotFoundException::new);
    checkIsWriter(boardUpdateRequest, board);

    List<String> imageCloudPathList =
        deleteLegacyImageListEntityandreuploadImage(boardUpdateRequest, boardId, board);

    return BoardDetailResponse.builder()
        .userName(loggedInUser.getName())
        .title(board.getTitle())
        .content(board.getContent())
        .boardImages(
            board.getBoardImages().stream().map(Image::getCloudPath).collect(Collectors.toList()))
        .createdDate(board.getCreatedDate())
        .updatedDate(board.getUpdatedDate())
        .boardImages(imageCloudPathList)
        .build();
  }

  private List<String> deleteLegacyImageListEntityandreuploadImage(
      BoardUpdateRequest boardUpdateRequest, Long boardId, Board board) {
    List<Image> imageList = imageService.findImageByBoardId(boardId);
    imageList.forEach(image -> imageService.deleteS3Image(image.getName()));
    imageService.deleteAllImage(imageList);

    List<Image> updatedImages =
        uploadImageS3andSaveEntity(board, boardUpdateRequest.getBoardImages());
    return updatedImages.stream().map(Image::getCloudPath).collect(Collectors.toList());
  }

  public void deleteBoard(Long boardId, Long userId) {
    Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
    if (!Objects.equals(userId, board.getUser().getId())) {
      throw new CantUpdateOthersBoard();
    }
    board.deleteBoard();
    boardRepository.save(board);
    List<Image> imageList = imageService.findImageByBoardId(boardId);
    imageList.forEach(image -> imageService.deleteS3Image(image.getName()));
    imageService.deleteAllImage(imageList);
  }

  private List<Image> uploadImageS3andSaveEntity(Board board, List<MultipartFile> boardImages) {
    final List<Image> updatedImageList =
        boardImages.stream().map(imageService::uploadToS3).collect(Collectors.toList());
    updatedImageList.forEach(image -> image.setBoard(board));
    return imageService.saveAllImage(updatedImageList);
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
