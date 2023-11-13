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

    public Board selectByNum(Long boardNum) { //게시물 번호로 한 개 게시물 가져오기
        return boardMapper.selectByNum(boardNum);
    }

    public void write(Board board, int empId) { //게시글 작성
        boardMapper.write(board, empId);
    }

    public void update(Board board) { //게시글 업데이트
        boardMapper.update(board);
    }

    public void delete(Long boardNum, int parentNum) { //게시글 삭제

        if(parentNum == boardNum){
            boardMapper.deleteParentNum(boardNum, parentNum);
        }else {
            boardMapper.deleteBoardNum(boardNum, parentNum);
        }
    }

    public void hitPlus(Long boardNum) { //조회수 증가
        boardMapper.hitPlus(boardNum);
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

    public void parentNumUpdate(Long boardNum) { //게시물 작성시 parentnum업데이트
        boardMapper.parentNumUpdate(boardNum);
    }

    public void replyWrite(Board pBoard, Board board, int empId) { //Q&A 답변
        boardMapper.replyWrite(board, pBoard.getParentNum(), pBoard.getHierarchyNum()+1, pBoard.getPEmpId(), empId);
    }

    public int selectCount1(String boardDivision) { //게시물 개수
        return boardMapper.selectCount1(boardDivision);
    }

    public int selectCount2(String boardDivision, String find, String searchOption) { //게시물 개수
        String f = "%" + find + "%";

        return boardMapper.selectCount2(boardDivision, f, searchOption);
    }

    public List<Board> select1(int startRow, int size) { //게시물 페이징
        return boardMapper.select1(startRow, size);
    }

    public List<Board> select2(int startRow, int size, String boardDivision) { //게시물 페이징
        return boardMapper.select2(startRow, size, boardDivision);
    }

    public List<Board> select3(int startRow, int size, String boardDivision, String find, String searchOption) { //검색 게시물 페이징
        String f = "%" + find + "%";
        return boardMapper.select3(startRow, size, boardDivision, f, searchOption);
    }

    public Emp selectEmp(int empId) { //사원 번호로 사원 정보 가져오기
        return boardMapper.selectEmp(empId);
    }

    public int maxHierarchyNum(int parentNum) {
        return boardMapper.maxHierarchyNum(parentNum);
    }
}
