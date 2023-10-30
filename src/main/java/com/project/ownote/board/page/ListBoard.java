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

    public BoardPage getBoardPage(Long boardnum, String boarddivision){
        int total = boardService.selectCount1(boarddivision);
        List<Board> content = boardService.select2(((boardnum.intValue()) - 1) * size, size, boarddivision);
        return new BoardPage(total,boardnum.intValue(), size, content);
    }

    public BoardPage getBoardPage(Long boardnum, String boarddivision, String find, String searchOption){
        int total = boardService.selectCount2(boarddivision, find, searchOption);
        List<Board> content = boardService.select3(((boardnum.intValue()) - 1) * size, size, boarddivision, find, searchOption);
        return new BoardPage(total,boardnum.intValue(), size, content);
    }
}
