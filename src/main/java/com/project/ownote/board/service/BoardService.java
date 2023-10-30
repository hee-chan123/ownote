package com.project.ownote.board.service;

import com.project.ownote.board.dao.BoardMapper;
import com.project.ownote.board.dto.Board;
import com.project.ownote.emp.login.dto.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService{

    @Autowired
    private BoardMapper boardMapper;

    public List<Board> selectAll() { //전체 게시물
        return boardMapper.selectAll();
    }

    public Board selectByNum(Long boardnum) { //게시물 번호로 한 개 게시물 가져오기
        return boardMapper.selectByNum(boardnum);
    }

    public void write(Board board, int empid) { //게시글 작성
        boardMapper.write(board, empid);
    }

    public void update(Board board) { //게시글 업데이트
        boardMapper.update(board);
    }

    public void delete(Long boardnum, int parentnum) { //게시글 삭제

        if(parentnum == boardnum){
            boardMapper.deleteParentNum(boardnum, parentnum);
        }else {
            boardMapper.deleteBoardNum(boardnum, parentnum);
        }
    }

    public void hitPlus(Long boardnum) { //조회수 증가
        boardMapper.hitPlus(boardnum);
    }

//    @Override
//    public List<Board> findLike(String boardDivision, String find) { //게시물 제목으로 검색
//        String sql ="";
//        String param ="";
//        switch (boardDivision) {
//            case "전체": {
//                sql = "select * from board where boardtitle like ? order by boardnum desc";
//                param = "%" + find + "%";
//                break;
//            }
//            case "회사뉴스및공지": {
//                sql = "select * from board where boarddivision = '회사뉴스및공지' and  boardtitle like ? order by boardnum desc";
//                param = "%" + find + "%";
//                break;
//            }
//            case "자유게시판": {
//                sql = "select * from board where boarddivision = '자유게시판' and  boardtitle like ? order by boardnum desc";
//                param = "%" + find + "%";
//                break;
//            }
//            case "사내시스템/F&Q": {
//                sql = "select * from board where boarddivision = '사내시스템/F&Q' and  boardtitle like ? order by boardnum desc";
//                param = "%" + find + "%";
//                break;
//            }
//        }
//
//        return boardDao.findLike(sql, param);
//    }

    public Long maxBoardNum() { //게시물 최대번호
        return boardMapper.maxBoardNum();
    }

    public void parentNumUpdate(Long boardnum) { //게시물 작성시 parentnum업데이트
        boardMapper.parentNumUpdate(boardnum);
    }

    public void replywrite(Board pBoard, Board board, int empid) { //Q&A 답변
        boardMapper.replywrite(board, pBoard.getParentnum(), pBoard.getHierarchynum()+1, pBoard.getPempid(), empid);
    }

    public int selectCount1(String boarddivision) { //게시물 개수
        return boardMapper.selectCount1(boarddivision);
    }

    public int selectCount2(String boarddivision, String find, String searchOption) { //게시물 개수
        String f = "%" + find + "%";

        return boardMapper.selectCount2(boarddivision, f, searchOption);
    }

    public List<Board> select1(int startRow, int size) { //게시물 페이징
        return boardMapper.select1(startRow, size);
    }

    public List<Board> select2(int startRow, int size, String boarddivision) { //게시물 페이징
        return boardMapper.select2(startRow, size, boarddivision);
    }

    public List<Board> select3(int startRow, int size, String boarddivision, String find, String searchOption) { //검색 게시물 페이징
        String f = "%" + find + "%";
        return boardMapper.select3(startRow, size, boarddivision, f, searchOption);
    }

    public Emp selectEmp(int empid) { //사원 번호로 사원 정보 가져오기
        return boardMapper.selectEmp(empid);
    }

    public int maxHierarchynum(int parentnum) {
        return boardMapper.maxHierarchynum(parentnum);
    }
}
