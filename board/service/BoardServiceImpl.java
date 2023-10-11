package com.project.ownote.board.service;

import com.project.ownote.board.dao.BoardDao;
import com.project.ownote.board.dto.Board;
import com.project.ownote.emp.login.dto.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardDao boardDao;

    @Override
    public List<Board> selectAll() { //전체 게시물
        return boardDao.selectAll();
    }

    @Override
    public Board selectByNum(Long boardNum) { //게시물 번호로 한 개 게시물 가져오기
        return boardDao.selectByNum(boardNum);
    }

    @Override
    public void write(Board board, int empId) { //게시글 작성
        boardDao.write(board, empId);
    }

    @Override
    public void update(Board board) { //게시글 업데이트
        boardDao.update(board);
    }

    @Override
    public void delete(Long boardNum) { //게시글 삭제
        boardDao.delete(boardNum);
    }

    @Override
    public void hitPlus(Long boardNum) { //조회수 증가
        boardDao.hitPlus(boardNum);
    }

    @Override
    public List<Board> findLike(String boardDivision, String find) { //게시물 제목으로 검색
        String sql ="";
        String param ="";
        switch (boardDivision) {
            case "전체": {
                sql = "select * from board where boardtitle like ? order by boardnum desc";
                param = "%" + find + "%";
                break;
            }
            case "공지사항": {
                sql = "select * from board where boarddivision = '공지사항' and  boardtitle like ? order by boardnum desc";
                param = "%" + find + "%";
                break;
            }
            case "자유게시판": {
                sql = "select * from board where boarddivision = '자유게시판' and  boardtitle like ? order by boardnum desc";
                param = "%" + find + "%";
                break;
            }
            case "Q&A": {
                sql = "select * from board where boarddivision = 'Q&A' and  boardtitle like ? order by boardnum desc";
                param = "%" + find + "%";
                break;
            }
        }

        return boardDao.findLike(sql, param);
    }

    @Override
    public Long maxBoardNum() { //게시물 최대번호
        return boardDao.maxBoardNum();
    }

    @Override
    public void parentNumUpdate(Long boardNum) { //게시물 작성시 parentnum업데이트
        boardDao.parentNumUpdate(boardNum);
    }

    @Override
    public void replywrite(Board pBoard, Board board, int empId) { //Q&A 답변
        boardDao.replywrite(pBoard, board, empId);
    }

    @Override
    public int selectCount(String boardDivision) { //게시물 개수
        return boardDao.selectCount(boardDivision);
    }

    @Override
    public int selectCount(String boardDivision, String find) { //게시물 개수
        return boardDao.selectCount(boardDivision, find);
    }

    @Override
    public List<Board> select(int startRow, int size) { //게시물 페이징
        return boardDao.select(startRow, size);
    }

    @Override
    public List<Board> select(int startRow, int size, String boardDivision) { //게시물 페이징
        String sql = "";
        switch (boardDivision) {
            case "공지사항": {
                sql = "select * from board where boarddivision = '공지사항' order by boardimportant desc, boardnum desc limit ?, ?";
                break;
            }
            case "자유게시판": {
                sql = "select * from board where boarddivision = '자유게시판' order by boardnum desc limit ?, ?";
                break;
            }
            case "Q&A": {
                sql = "select * from board where boarddivision = 'Q&A' order by parentnum desc, hierarchynum limit ?, ?";
                break;
            }
        }
        return boardDao.select(startRow, size, sql);
    }

    @Override
    public List<Board> select(int startRow, int size, String boardDivision, String find) { //검색 게시물 페이징
        String sql = "";
        String f = "%" + find + "%";
        switch (boardDivision) {
            case "공지사항": {
                sql = "select * from board where boarddivision = '공지사항' and boardtitle like ? order by boardimportant desc, boardnum desc limit ?, ?";
                break;
            }
            case "자유게시판": {
                sql = "select * from board where boarddivision = '자유게시판' and boardtitle like ? order by boardnum desc limit ?, ?";
                break;
            }
            case "Q&A": {
                sql = "select * from board where boarddivision = 'Q&A' and boardtitle like ? order by parentnum desc, hierarchynum limit ?, ?";
                break;
            }
        }
        return boardDao.select(startRow, size, sql, f);
    }

    @Override
    public Emp selectEmp(int empId) { //사원 번호로 사원 정보 가져오기
        return boardDao.selectEmp(empId);
    }
}
