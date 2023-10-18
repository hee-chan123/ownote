package com.project.ownote.board.service;

import com.project.ownote.board.dto.Board;
import com.project.ownote.emp.login.dto.Emp;

import java.util.List;

public interface BoardService {

    public List<Board> selectAll(); //전체 게시물

    public Board selectByNum(Long boardNum); //게시물 번호로 한 개 게시물 가져오기

    public void write(Board board, int empId); //게시글 작성

    public void update(Board board); //게시글 업데이트

    public void delete(Long boardNum, int parentNum); //게시글 삭제

    public void hitPlus(Long boardNum); //조회수 증가

//    public List<Board> findLike(String boardDivision, String find); //게시물 제목으로 검색

    public Long maxBoardNum(); //게시물 최대번호

    public void parentNumUpdate(Long boardNum); //게시물 작성시 parentnum업데이트

    public void replywrite(Board pBoard, Board board, int empId); //Q&A 답변

    public int selectCount(String boardDivision); //게시물 개수

    public int selectCount(String boardDivision, String find, String searchOption); //게시물 개수

    public List<Board> select(int startRow, int size); //게시물 페이징

    public List<Board> select(int startRow, int size, String boardDivision); //게시물 페이징

    public List<Board> select(int startRow, int size, String boardDivision, String find, String searchOption); //검색 게시물 페이징

    public Emp selectEmp(int empId); //사원 번호로 사원 정보 가져오기

    public int maxHierarchynum (int parentNum); //maxHierarchynum
}
