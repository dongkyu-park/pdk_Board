package com.gn.board.controller;

import com.gn.board.domain.Board;
import com.gn.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/add")
    public String addForm() {
        return "addForm";
    }

    @PostMapping("/board/add")
    public String addBoard(@ModelAttribute Board board) {
        Long savedId = boardService.save(board);
        return "redirect:/";
    }
}
