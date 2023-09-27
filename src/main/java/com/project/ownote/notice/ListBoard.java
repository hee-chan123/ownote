package com.project.ownote.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListBoard {

    @Autowired
    private BoardDao boardDao;

    private int size = 10;

    public BoardPage getBoardPage(Long boardNum){
        int total = boardDao.selectCount();
        List<Board> content = boardDao.select((int) (boardNum - 1 * size), size);
        return new BoardPage(total,boardNum.intValue(), size, content);
    }
}
