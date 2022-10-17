package com.shoplive.codingtest.domain.board.domain.repository;

import com.shoplive.codingtest.domain.board.dto.BoardPreviewInfo;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryQuerydsl {

  List<BoardPreviewInfo> findBoardPreviewInfoListPage(Pageable pageable, boolean isTimeReversed);

  List<BoardPreviewInfo> getBoardListSearchTitleOrContent(
      Pageable pageable, boolean isTimeReversed, String keyword);
}
