package com.gn.board.repository;

import com.gn.board.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    Long save(Board board);

    List<Board> findBoards(List<Long> boardIds);

    Board findById(Long boardId);
}
