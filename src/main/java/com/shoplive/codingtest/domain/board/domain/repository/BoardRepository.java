package com.shoplive.codingtest.domain.board.domain.repository;

import com.shoplive.codingtest.domain.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "select b from Board b where b.id = :boardId and b.isRemoved = false")
    Optional<Board> findByIdAndRemoved(@Param("boardId") Long boardId);
}
