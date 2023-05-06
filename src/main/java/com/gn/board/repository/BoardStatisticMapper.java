package com.gn.board.repository;

import com.gn.board.domain.RelatedBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardStatisticMapper {

    void saveContentWordCount(Long id, List<Map.Entry<String, Long>> wordCounts);

    void saveAndUpdateUseWordCount(List<String> words);

    int updateBoardCount(Long count);

    Long getBoardCount();

    List<String> findRelatedWords(Long standardNumberOfAssociateWord);

    List<RelatedBoard> findRelatedBoard(List<String> relatedWords);
}
