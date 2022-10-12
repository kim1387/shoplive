package com.shoplive.codingtest.domain.board.domain.repository;

import com.shoplive.codingtest.domain.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
