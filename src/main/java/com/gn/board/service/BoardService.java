package com.gn.board.service;

import com.gn.board.domain.Board;
import com.gn.board.repopsitory.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    public static final Long ADD_ONE_COUNT = 1L;

    private final BoardMapper boardRepository;

    @Transactional
    public Long save(Board board) {
        Long savedId = boardRepository.save(board);
        saveContentWordCount(board);
        saveAndUpdateUseWordCount(board);
        increaseBoardCount();
        return savedId;
    }

    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    private void saveAndUpdateUseWordCount(Board board) {
        String[] arrWords = board.getContent().trim().split(" ");
        List<String> words = Arrays.stream(arrWords).distinct().toList();

        boardRepository.saveAndUpdateUseWordCount(words);
    }

    private void saveContentWordCount(Board board) {
        String[] arrWords = board.getContent().trim().split(" ");

        List<Map.Entry<String, Long>> wordCounts = Arrays.stream(arrWords)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.toList());

        boardRepository.saveContentWordCount(board.getId(), wordCounts);
    }

    private void increaseBoardCount() {
        boardRepository.updateBoardTotal(ADD_ONE_COUNT);
    }
}
