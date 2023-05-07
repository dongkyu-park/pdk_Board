package com.gn.board.service;

import com.gn.board.domain.Board;
import com.gn.board.domain.RelatedBoard;
import com.gn.board.domain.RelatedBoardInfo;
import com.gn.board.repository.BoardMapper;
import com.gn.board.repository.BoardStatisticMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    public static final Long ADD_ONE_COUNT = 1L;
    public static final double STANDARD_PERCENTAGE_BY_RELATED_WORD = 0.4;
    public static final int STANDARD_COUNT_OF_RELATED_BOARD_HAS_SAME_WORD = 2;

    private final BoardMapper boardRepository;
    private final BoardStatisticMapper boardStatisticRepository;

    @Transactional
    public Long save(Board board) {
        Long savedId = boardRepository.save(board);
        saveContentWordCount(board);
        saveAndUpdateUseWordCount(board);
        increaseBoardCount();

        return savedId;
    }

    public List<Board> findBoards(List<Long> boardIds) {
        return boardRepository.findBoards(boardIds);
    }

    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public List<RelatedBoardInfo> findRelatedBoards(Board board) {
        List<String> contentWords = getContentWordList(board);

        // 전체 게시글 갯수에서 40%에 해당하는 갯수를 계산한다.
        Long boardCount = boardStatisticRepository.getBoardCount();
        Long standardNumberOfAssociateWord = Math.round(boardCount * STANDARD_PERCENTAGE_BY_RELATED_WORD);

        // 상세 조회한 게시글에서 사용 된 단어 중, 전체 게시글에서 40% 이하의 갯수로 사용된 단어 목록을 찾는다.
        List<String> relatedWords = boardStatisticRepository.findRelatedWords(standardNumberOfAssociateWord);
        relatedWords.retainAll(contentWords);

        // 단어 목록이 비어 있다면 연관 게시글 없음.
        if (relatedWords.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        // 연관 단어 목록을 검색 조건으로 조회한, 게시글마다 해당 단어 사용 빈도 수의 데이터를 역직렬화 하기 위한 용도
        Map<Long, RelatedBoardInfo> combineSameBoardId = new HashMap<>();

        List<RelatedBoard> relatedBoards = boardStatisticRepository.findRelatedBoard(relatedWords);
        relatedBoards.stream()
                .forEach(relatedBoardInfo -> {
                    if (combineSameBoardId.containsKey(relatedBoardInfo.getBoardId())) {
                        combineSameBoardId.get(relatedBoardInfo.getBoardId()).increaseRelatedWordCount();
                        return;
                    }
                    combineSameBoardId.put(relatedBoardInfo.getBoardId(), new RelatedBoardInfo(relatedBoardInfo.getBoardId(),
                                    relatedBoardInfo.getTitle(), relatedBoardInfo.getContent(),
                                    relatedBoardInfo.getCreateDt(), relatedBoardInfo.getUpdateDt(),
                                    relatedBoardInfo.getWord(), relatedBoardInfo.getWordCount()
                            )
                    );
                });

        List<RelatedBoardInfo> result = combineSameBoardId.entrySet().stream()
                .filter(e -> e.getValue().getRelatedWordTotal() >= STANDARD_COUNT_OF_RELATED_BOARD_HAS_SAME_WORD) // 연관 단어가 2회 이상 사용된 경우만 필터링
                .filter(e -> e.getValue().getBoard().getId() != board.getId()) // 클릭한 게시글은 연관 게시글에서 제외
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .map(e -> e.getValue())
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return result;
    }

    private void saveAndUpdateUseWordCount(Board board) {
        List<String> contentWords = getContentWordList(board);
        boardStatisticRepository.saveAndUpdateUseWordCount(contentWords);
    }

    private static List<String> getContentWordList(Board board) {
        String[] arrWords = board.getContent().trim().split(" ");
        List<String> contentWords = Arrays.stream(arrWords).distinct().toList();
        return contentWords;
    }

    private void saveContentWordCount(Board board) {
        String[] arrWords = board.getContent().trim().split(" ");

        List<Map.Entry<String, Long>> wordCounts = Arrays.stream(arrWords)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.toList());

        boardStatisticRepository.saveContentWordCount(board.getId(), wordCounts);
    }

    private void increaseBoardCount() {
        boardStatisticRepository.updateBoardCount(ADD_ONE_COUNT);
    }
}
