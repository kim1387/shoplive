package com.shoplive.codingtest.domain.board.domain.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoplive.codingtest.domain.board.dto.BoardPreviewInfo;
import com.shoplive.codingtest.domain.board.dto.QBoardPreviewInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.shoplive.codingtest.domain.board.domain.entity.QBoard.board;
import static com.shoplive.codingtest.domain.image.domain.entity.QImage.image;
import static com.shoplive.codingtest.domain.user.domain.entity.QUser.user;

@RequiredArgsConstructor
public class BoardRepositoryQuerydslImpl implements BoardRepositoryQuerydsl {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<BoardPreviewInfo> findBoardPreviewInfoListPage(
      Pageable pageable, boolean isTimeReversed) {
    return queryFactory
        .select(
            new QBoardPreviewInfo(
                board.title,
                board.user.name,
                board.createdDate,
                JPAExpressions.selectFrom(image).where(image.board.eq(board)).exists()))
        .from(board)
        .where(board.isRemoved.eq(false))
        .innerJoin(board.user, user)
        .innerJoin(board.boardImages, image)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(boardSort(isTimeReversed))
        .distinct()
        .fetch();
  }

  @Override
  public List<BoardPreviewInfo> getBoardListSearchTitleOrContent(
      Pageable pageable, boolean isTimeReversed, String keyword) {
    return queryFactory
        .select(
            new QBoardPreviewInfo(
                board.title,
                board.user.name,
                board.createdDate,
                JPAExpressions.selectFrom(image).where(image.board.eq(board)).exists()))
        .from(board)
        .where(
            board
                .isRemoved
                .eq(false)
                .and((board.title.contains(keyword)).or(board.content.contains(keyword))))
        .innerJoin(board.user, user)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(boardSort(isTimeReversed))
        .fetch();
  }

  private OrderSpecifier<?> boardSort(boolean isTimeReversed) {
    Order order = isTimeReversed ? Order.ASC : Order.DESC;
    return new OrderSpecifier<>(order, board.createdDate);
  }
}
