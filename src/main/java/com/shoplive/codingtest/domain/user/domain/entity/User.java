package com.shoplive.codingtest.domain.user.domain.entity;

import com.shoplive.codingtest.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 유저명
  @Column(name = "name", nullable = false)
  private String name;

  // 유저 권한
  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private UserRole userRole;

  // 활성 유저 여부 체크
  @Column(name = "is_activated", nullable = false)
  private boolean isActivated;

  @Builder
  public User(String name, UserRole userRole) {
    this.name = name;
    this.userRole = userRole;
    this.isActivated = true;
  }
}
