package com.gn.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RelatedBoard {

    private Long boardId;

    private String word;

    private int wordCount;

    private String title;

    private String content;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;
}
