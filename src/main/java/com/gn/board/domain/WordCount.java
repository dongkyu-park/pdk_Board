package com.gn.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WordCount implements Comparable<WordCount> {

    private String word;

    private int wordCount;

    @Override
    public int compareTo(WordCount wordCount) {
        if (this.wordCount > wordCount.getWordCount()) {
            return 1;
        }
        if (this.wordCount < wordCount.getWordCount()) {
            return -1;
        }
        return 0;
    }
}
