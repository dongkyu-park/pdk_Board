package com.gn.board.repopsitory;

import com.gn.board.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    Long save(Board board);

    void saveContentWordCount(Long id, List<Map.Entry<String, Long>> wordCounts);

    void saveAndUpdateUseWordCount(List<String> words);

    int updateBoardTotal(Long count);
}
