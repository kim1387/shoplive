package com.shoplive.codingtest.domain.board.domain.repository;

import com.shoplive.codingtest.domain.board.dto.BoardPreviewInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryQuerydsl{


    List<BoardPreviewInfo> findBoardPreviewInfoListPage(Pageable pageable, boolean isTimeReversed);
    List<BoardPreviewInfo> getBoardListSearchTitleOrContent(Pageable pageable, boolean isTimeReversed, String keyword);
}
