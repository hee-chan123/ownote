package com.project.ownote.board.page;

import com.project.ownote.board.dto.Board;
import com.project.ownote.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListBoard {

    @Autowired
    private BoardService boardService;

    private int size = 10;

//    public BoardPage getBoardPage(Long boardNum){
//        int total = boardDao.selectCount();
//        List<Board> content = boardDao.select(((boardNum.intValue()) - 1) * size, size);
//        return new BoardPage(total,boardNum.intValue(), size, content);
//    }

    public BoardPage getBoardPage(Long boardNum, String boardDivision){
        int total = boardService.selectCount(boardDivision);
        List<Board> content = boardService.select(((boardNum.intValue()) - 1) * size, size, boardDivision);
        return new BoardPage(total,boardNum.intValue(), size, content);
    }

    public BoardPage getBoardPage(Long boardNum, String boardDivision, String find, String searchOption){
        int total = boardService.selectCount(boardDivision, find, searchOption);
        List<Board> content = boardService.select(((boardNum.intValue()) - 1) * size, size, boardDivision, find, searchOption);
        return new BoardPage(total,boardNum.intValue(), size, content);
    }
}
