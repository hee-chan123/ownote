package com.project.ownote.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListBoard {

    @Autowired
    private BoardDao boardDao;

    private int size = 10;

//    public BoardPage getBoardPage(Long boardNum){
//        int total = boardDao.selectCount();
//        List<Board> content = boardDao.select(((boardNum.intValue()) - 1) * size, size);
//        return new BoardPage(total,boardNum.intValue(), size, content);
//    }

    public BoardPage getBoardPage(Long boardNum, String boardDivision){
        int total = boardDao.selectCount(boardDivision);
        List<Board> content = boardDao.select(((boardNum.intValue()) - 1) * size, size);
        return new BoardPage(total,boardNum.intValue(), size, content);
    }
}
