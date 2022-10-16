package com.shoplive.codingtest.domain.board.domain.entity;

import com.shoplive.codingtest.domain.image.domain.entity.Image;
import com.shoplive.codingtest.domain.user.domain.entity.User;
import com.shoplive.codingtest.global.entity.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
public class Board extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 게시판 제목
  @Column(name = "title", nullable = false)
  private String title;

  // 게시판 내용
  @Lob
  @Column(name = "content", nullable = false)
  private String content;

  // 게시판 삭제 유무
  @Column(name = "is_removed", nullable = false)
  private boolean isRemoved;

  // 게시글 작성자
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  // 게시물과 이미지 연관관계
  @OneToMany(mappedBy = "board")
  private List<Image> boardImages = new ArrayList<>();

  @Builder
  public Board(String title, String content, User user) {
    this.title = title;
    this.content = content;
    this.user = user;
    this.isRemoved = false;
  }

  public void deleteBoard() {
    this.isRemoved = true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Board board = (Board) o;
    return isRemoved == board.isRemoved
        && Objects.equals(id, board.id)
        && Objects.equals(title, board.title)
        && Objects.equals(content, board.content)
        && Objects.equals(user, board.user)
        && Objects.equals(boardImages, board.boardImages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, content, isRemoved, user, boardImages);
  }
}
