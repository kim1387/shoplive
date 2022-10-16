package com.shoplive.codingtest.domain.board.domain.repository;

import com.shoplive.codingtest.domain.board.domain.entity.Board;
import com.shoplive.codingtest.domain.board.exception.BoardNotFoundException;
import com.shoplive.codingtest.domain.image.domain.entity.Image;
import com.shoplive.codingtest.domain.image.domain.repository.ImageRepository;
import com.shoplive.codingtest.domain.user.domain.entity.User;
import com.shoplive.codingtest.domain.user.domain.entity.UserRole;
import com.shoplive.codingtest.domain.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BoardRepositoryTest {

  @Autowired private BoardRepository boardRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private ImageRepository imageRepository;

  private Image givenImage;
  private Board givenBoard;
  private Board givenFailedBoard;
  private User givenUser;

  @BeforeEach
  void setUp() {

    String givenUUID = UUID.randomUUID().toString();
    givenUser = User.builder().userRole(UserRole.USER).isActivated(true).name("김기현").build();

    givenBoard = Board.builder().title("제목").content("내용").user(givenUser).build();

    givenImage =
        Image.builder()
            .id(givenUUID)
            .board(givenBoard)
            .name("image name")
            .cloudPath("test cloud path")
            .build();

    givenUser = userRepository.save(givenUser);
    givenBoard = boardRepository.save(givenBoard);
    givenImage = imageRepository.save(givenImage);
  }

  @Test
  @DisplayName("save 메서드 테스트")
  void saveTest() {
    // given

    // when
    Board expectedBoard = boardRepository.save(givenBoard);
    // then
    Assertions.assertAll(
        () -> assertEquals(givenBoard, expectedBoard),
        () -> assertEquals(givenBoard.getTitle(), expectedBoard.getTitle()),
        () -> assertEquals(givenBoard.getContent(), expectedBoard.getContent()));
  }

  @Test
  @DisplayName("Id로 Board 찾는 매서드")
  void findByIdTest() {
    // given

    // when
    Board expectedBoard = boardRepository.findById(1L).orElseThrow(BoardNotFoundException::new);
    // then

    Assertions.assertEquals(expectedBoard, givenBoard);
  }

  @Test
  @DisplayName("Id로 Board 찾는 매서드 예외")
  void findByIdTestFailByBoardNotFoundException() {
    // given

    // when

    // then
    Assertions.assertThrows(
        BoardNotFoundException.class,
        () -> {
          boardRepository.findById(100L).orElseThrow(BoardNotFoundException::new);
        });
  }

  @Test
  @DisplayName("board와 함께 이미지 조회")
  void findBoardWithImageByBoardIdTest() {
    //    // given
    //
    //    // when
    //    Board expectedBoard =
    //        boardRepository
    //            .findBoardWithImageByBoardId(givenBoard.getId())
    //            .orElseThrow(BoardNotFoundException::new);
    //    List<Image> expectedImageList = expectedBoard.getBoardImages();
    //    // then
    //    List<Image> actualImageList =
    //
    // imageRepository.findAllByBoardId(expectedBoard.getId()).orElseThrow(RuntimeException::new);
    //    Assertions.assertAll(
    //        () -> assertEquals(expectedBoard, givenBoard),
    //        () -> assertEquals(expectedImageList, actualImageList));
  }

  @Test
  @DisplayName("삭제된 글은 조회하지 않는 메서드")
  void findByIdAndRemovedIsFalseTest() {
    // given

    // when
    Board orginalBoard =
        boardRepository.findByIdAndRemoved(1L).orElseThrow(BoardNotFoundException::new);

    // then
    Assertions.assertAll(() -> assertEquals(orginalBoard, givenBoard));
  }

  @Test
  @DisplayName("삭제된 게시판은 출력하지 않음 예외상황")
  void BoardRepositoryTest() {
    // given
    Board givenDeletedBoard = Board.builder().title("제목1").content("1").user(givenUser).build();
    givenDeletedBoard.deleteBoard();
    givenDeletedBoard = boardRepository.save(givenDeletedBoard);
    // when

    // then
    Assertions.assertAll(
        () ->
            assertThrows(
                BoardNotFoundException.class,
                () -> {
                  boardRepository
                      .findByIdAndRemoved(2L)
                      .orElseThrow(BoardNotFoundException::new);
                }));
  }
}
