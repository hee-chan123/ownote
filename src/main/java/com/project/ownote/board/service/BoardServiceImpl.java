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
    public void delete(Long boardNum, int parentNum) { //게시글 삭제
        String sql = "";

        if(parentNum == boardNum){
            sql = "delete from board where parentnum = ?";
        }else {
            sql = "delete from board where boardnum = ?";
        }
        boardDao.delete(boardNum, sql);
    }

    @Override
    public void hitPlus(Long boardNum) { //조회수 증가
        boardDao.hitPlus(boardNum);
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
    public int selectCount(String boardDivision, String find, String searchOption) { //게시물 개수
        String sql = "";
        String f = "%" + find + "%";

        if (boardDivision.equals("전체")) {
            sql = "select count(*) from board where ";
        } else {
            sql = "select count(*) from board where boardDivision = ? and ";
        }

        switch (searchOption) {
            case "전체": {
                sql += "(boardtitle like ? or boardwriter like ? or boardcontent like ?)";
                break;
            }
            case "제목": {
                sql += "boardtitle like ?";
                break;
            }
            case "작성자": {
                sql += "boardwriter like ?";
                break;
            }
            case "내용": {
                sql += "boardcontent like ?";
                break;
            }
            case "제목+내용": {
                sql += "(boardtitle like ? or boardcontent like ?)";
                break;
            }
        }
        return boardDao.selectCount(boardDivision, f, searchOption, sql);
    }

    @Override
    public List<Board> select(int startRow, int size) { //게시물 페이징
        return boardDao.select(startRow, size);
    }

    @Override
    public List<Board> select(int startRow, int size, String boardDivision) { //게시물 페이징
        String sql = "";
        switch (boardDivision) {
            case "회사뉴스및공지": {
                sql = "select * from board where boarddivision = '회사뉴스및공지' order by boardimportant desc, boardnum desc limit ?, ?";
                break;
            }
            case "자유게시판": {
                sql = "select * from board where boarddivision = '자유게시판' order by boardnum desc limit ?, ?";
                break;
            }
            case "사내시스템/F&Q": {
                sql = "select * from board where boarddivision = '사내시스템/F&Q' order by parentnum desc, hierarchynum limit ?, ?";
                break;
            }
        }
        return boardDao.select(startRow, size, sql);
    }

    @Override
    public List<Board> select(int startRow, int size, String boardDivision, String find, String searchOption) { //검색 게시물 페이징
        String sql = "";
        String sql2 = "";
        String f = "%" + find + "%";

        switch (boardDivision) {
            case "전체": {
                sql = "select * from board where ";
                sql2 = "order by boardimportant desc, parentnum desc, hierarchynum, boardnum desc limit ?, ?";
                break;
            }
            case "회사뉴스및공지": {
                sql = "select * from board where boarddivision = '회사뉴스및공지' and ";
                sql2 = "order by boardimportant desc, boardnum desc limit ?, ?";
                break;
            }
            case "자유게시판": {
                sql = "select * from board where boarddivision = '자유게시판' and ";
                sql2 = "order by boardnum desc limit ?, ?";
                break;
            }
            case "사내시스템/F&Q": {
                sql = "select * from board where boarddivision = '사내시스템/F&Q' and ";
                sql2 = "order by parentnum desc, hierarchynum limit ?, ?";
                break;
            }
        }

        switch (searchOption) {
            case "전체": {
                sql += "(boardtitle like ? or boardwriter like ? or boardcontent like ?) ";
                break;
            }
            case "제목": {
                sql += "boardtitle like ? ";
                break;
            }
            case "작성자": {
                sql += "boardwriter like ? ";
                break;
            }
            case "내용": {
                sql += "boardcontent like ? ";
                break;
            }
            case "제목+내용": {
                sql += "(boardtitle like ? or boardcontent like ?) ";
                break;
            }
        }
        sql += sql2;

        return boardDao.select(startRow, size, sql, f, searchOption);
    }

    @Override
    public Emp selectEmp(int empId) { //사원 번호로 사원 정보 가져오기
        return boardDao.selectEmp(empId);
    }

    @Override
    public int maxHierarchynum(int parentNum) {
        return boardDao.maxHierarchynum(parentNum);
    }
}
