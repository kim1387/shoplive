package com.shoplive.codingtest.domain.user.domain.repository;

import com.shoplive.codingtest.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
