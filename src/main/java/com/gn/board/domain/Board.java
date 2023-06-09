package com.gn.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Board {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;
}
