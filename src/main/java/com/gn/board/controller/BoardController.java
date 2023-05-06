package com.gn.board.controller;

import com.gn.board.domain.Board;
import com.gn.board.domain.RelatedBoardInfo;
import com.gn.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/")
    public String boards(Model model) {
        List<Board> boards = boardService.findBoards(new ArrayList<>());
        model.addAttribute("boards", boards);
        return "boards";
    }

    @GetMapping("/board/{boardId}")
    public String board(@PathVariable Long boardId, Model model) {
        Board board = boardService.findBoardById(boardId);
        List<RelatedBoardInfo> relatedBoards = boardService.findRelatedBoards(board);
        model.addAttribute("board", board);
        model.addAttribute("relatedBoards", relatedBoards);
        return "board";
    }
}
