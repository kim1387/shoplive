package com.shoplive.codingtest.domain.image.domain.repository;

import com.shoplive.codingtest.domain.image.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<List<Image>> findAllByBoardId(Long boardId);
}
