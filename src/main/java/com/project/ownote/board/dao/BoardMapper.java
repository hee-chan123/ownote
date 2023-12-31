package com.project.ownote.board.dao;

import com.project.ownote.board.dto.Board;
import com.project.ownote.emp.login.dto.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<Board> selectAll(); //전체 게시물

    Board selectByNum(@Param("boardNum") Long boardNum); //게시물 번호로 한 개 게시물 가져오기

    void write(@Param("board") Board board, @Param("empId") int empId); //게시글 작성

    void update(Board board); //게시글 업데이트

    void deleteBoardNum(@Param("boardNum") Long boardNum, @Param("parentNum") int parentNum); //게시글 삭제

    void deleteParentNum(@Param("boardNum") Long boardNum, @Param("parentNum") int parentNum); //게시글 삭제

    void hitPlus(@Param("boardNum") Long boardNum); //조회수 증가

//    public List<Board> findLike(String boardDivision, String find); //게시물 제목으로 검색

    Long maxBoardNum(); //게시물 최대번호

    void parentNumUpdate(@Param("boardNum") Long boardNum); //게시물 작성시 parentnum업데이트

    void replyWrite(@Param("board") Board board, @Param("parentNum") int parentNum, @Param("hierarchyNum") int hierarchyNum, @Param("pEmpId") int pEmpId, @Param("empId") int empId); //Q&A 답변

    int selectCount1(@Param("boardDivision") String boardDivision); //게시물 개수

    int selectCount2(@Param("boardDivision") String boardDivision, @Param("find") String find, @Param("searchOption") String searchOption); //게시물 개수

    List<Board> select1(@Param("startRow") int startRow, @Param("size") int size); //게시물 페이징

    List<Board> select2(@Param("startRow") int startRow, @Param("size") int size, @Param("boardDivision") String boardDivision); //게시물 페이징

    List<Board> select3(@Param("startRow") int startRow, @Param("size") int size, @Param("boardDivision") String boardDivision, @Param("find") String find, @Param("searchOption") String searchOption); //검색 게시물 페이징

    Emp selectEmp(@Param("empId") int empId); //사원 번호로 사원 정보 가져오기

    int maxHierarchyNum (@Param("parentNum") int parentNum); //maxHierarchynum
}
