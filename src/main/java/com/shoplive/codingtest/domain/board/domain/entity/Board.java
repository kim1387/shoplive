package com.shoplive.codingtest.domain.board.domain.entity;

import com.shoplive.codingtest.domain.image.domain.entity.Image;
import com.shoplive.codingtest.domain.user.domain.entity.User;
import com.shoplive.codingtest.global.entity.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
