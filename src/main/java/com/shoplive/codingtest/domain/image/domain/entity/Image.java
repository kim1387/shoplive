package com.shoplive.codingtest.domain.image.domain.entity;

import com.shoplive.codingtest.domain.board.domain.entity.Board;
import com.shoplive.codingtest.global.entity.BaseCreatedEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseCreatedEntity {

  @Id private String id;

  // 이미지명
  @Column(name = "name", nullable = false)
  private String name;

  // 클라우드에 저장된 URL
  @Column(name = "cloud_path")
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

  public void setBoard(Board board) {
    this.board = board;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Image image = (Image) o;
    return isRemoved == image.isRemoved
        && Objects.equals(id, image.id)
        && Objects.equals(name, image.name)
        && Objects.equals(cloudPath, image.cloudPath)
        && Objects.equals(board, image.board);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, cloudPath, board, isRemoved);
  }
}
