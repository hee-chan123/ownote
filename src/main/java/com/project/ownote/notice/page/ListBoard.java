package com.project.ownote.notice.page;

import com.project.ownote.notice.dao.BoardDao;
import com.project.ownote.notice.dto.Board;
import com.project.ownote.notice.page.BoardPage;
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

    public BoardPage getBoardPage(Long boardNum, String boardDivision, String find){
        int total = boardDao.selectCount(boardDivision, find);
        List<Board> content = boardDao.select(((boardNum.intValue()) - 1) * size, size);
        return new BoardPage(total,boardNum.intValue(), size, content);
    }
}
