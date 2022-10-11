package com.shoplive.codingtest.domain.image.domain.entity;

import com.shoplive.codingtest.global.entity.BaseCreatedEntity;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseCreatedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 이미지명
  @Column(name = "name", nullable = false)
  private String name;

  // 이미지 파일 형식 유형
  @Column(name = "type", nullable = false)
  private String type;

  // 클라우드에 저장된 URL
  @Column(name = "cloud_path", nullable = false)
  private String cloudPath;

  // 이미지의 삭제 여부
  @Column(name = "is_removed", nullable = false)
  private boolean isRemoved;
}
