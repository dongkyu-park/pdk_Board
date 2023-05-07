package com.gn.board.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

@Getter
public class RelatedBoardInfo implements Comparable<RelatedBoardInfo> {

    private Board board;

    private int relatedWordTotal;

    private final PriorityQueue<WordCount> relatedWordEachCount = new PriorityQueue<>();

    public RelatedBoardInfo(Long id, String title, String content, LocalDateTime createDt, LocalDateTime updateDt, String word, int wordCount) {
        this.board = new Board(id, title, content, createDt, updateDt);
        this.relatedWordTotal = 1;
        this.relatedWordEachCount.add(new WordCount(word, wordCount));
    }

    public void increaseRelatedWordCount() {
        this.relatedWordTotal++;
    }

    /**
     * 연관도 설정을 위한 비교
     * 1. 연관 단어 사용 빈도 수가 높을 수록 우선도가 높다
     * 2. 빈도 수가 동일하다면, 가장 많이 사용 된 연관 단어의 갯수끼리 비교한다.
     * 3. 첫번째 비교된 연관 단어의 갯수가 동일하다면, 다음 단어를 가져와 비교한다.
     * 4. 연관 단어의 빈도 수가 동일하며, 연관 단어의 사용 횟수로 비교가 불가능한 경우, 동일한 우선순위로 설정한다.
     */
    @Override
    public int compareTo(RelatedBoardInfo relatedBoardInfo) {
        if (this.relatedWordTotal > relatedBoardInfo.getRelatedWordTotal()) {
            return 1;
        }
        if (this.relatedWordTotal < relatedBoardInfo.getRelatedWordTotal()) {
            return -1;
        }
        return selectPriorityRelatedBoard(relatedBoardInfo);
    }

    private int selectPriorityRelatedBoard(RelatedBoardInfo relatedBoardInfo) {
        if (this.relatedWordEachCount.peek() == null || relatedBoardInfo.getRelatedWordEachCount().peek() == null) {
            return 0;
        }
        if (this.relatedWordEachCount.peek().getWordCount() > relatedBoardInfo.getRelatedWordEachCount().peek().getWordCount()) {
            return 1;
        }
        if (this.relatedWordEachCount.peek().getWordCount() < relatedBoardInfo.getRelatedWordEachCount().peek().getWordCount()) {
            return -1;
        }
        this.relatedWordEachCount.remove();
        relatedBoardInfo.getRelatedWordEachCount().remove();

        return selectPriorityRelatedBoard(relatedBoardInfo);
    }
}
