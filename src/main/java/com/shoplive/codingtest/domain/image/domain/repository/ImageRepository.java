package com.shoplive.codingtest.domain.image.domain.repository;

import com.shoplive.codingtest.domain.image.domain.entity.Image;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

  Optional<List<Image>> findAllByBoardId(Long boardId);
}
