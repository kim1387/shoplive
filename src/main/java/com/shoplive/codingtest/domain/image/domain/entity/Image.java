package com.shoplive.codingtest.domain.image.domain.entity;

import com.shoplive.codingtest.domain.board.domain.entity.Board;
import com.shoplive.codingtest.global.entity.BaseCreatedEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseCreatedEntity {

  @Id
  private String id;

  // 이미지명
  @Column(name = "name", nullable = false)
  private String name;

  // 클라우드에 저장된 URL
  @Column(name = "cloud_path", nullable = false)
  private String cloudPath;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  // 이미지의 삭제 여부
  @Column(name = "is_removed", nullable = false)
  private boolean isRemoved;

  @Builder
  public Image(String id, String name, String cloudPath, Board board) {
    this.id = id;
    this.name = name;
    this.cloudPath = cloudPath;
    this.board = board;
    this.isRemoved = false;
  }
}
