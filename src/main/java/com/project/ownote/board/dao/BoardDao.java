package com.project.ownote.board.dao;

import com.project.ownote.board.dto.Board;
import com.project.ownote.emp.login.dto.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Board> selectAll(){ //전체 게시물
        String sql = "select * from board order by boardnum desc";
        List<Board> list = jdbcTemplate.query(sql, (rs, n) ->{
            Board board = new Board(
                    rs.getLong("boardNum"),
                    rs.getString("boardTitle"),
                    rs.getString("boardContent"),
                    rs.getString("boardWriter"),
                    rs.getString("boardDivision"),
                    rs.getTimestamp("boardRegDate").toLocalDateTime(),
                    rs.getInt("boardHit"),
                    rs.getInt("boardImportant"),
                    rs.getInt("parentNum"),
                    rs.getInt("hierarchyNum"),
                    rs.getInt("pEmpId"),
                    rs.getInt("empId"));
            return board;
        });
        return list;
    }

    public Board selectByNum(Long boardNum){ //게시물 번호로 한 개 게시물 가져오기
        String sql = "select * from board where boardNum = ?";
        List<Board> boards = jdbcTemplate.query(sql, (rs, n) -> {
            Board board = new Board(
                    rs.getLong("boardNum"),
                    rs.getString("boardTitle"),
                    rs.getString("boardContent"),
                    rs.getString("boardWriter"),
                    rs.getString("boardDivision"),
                    rs.getTimestamp("boardRegDate").toLocalDateTime(),
                    rs.getInt("boardHit"),
                    rs.getInt("boardImportant"),
                    rs.getInt("parentNum"),
                    rs.getInt("hierarchyNum"),
                    rs.getInt("pEmpId"),
                    rs.getInt("empId"));
            return board;

        }, boardNum) ;
        return boards.isEmpty() ? null : boards.get(0);
    }
    public void write(Board board, int empId){ //게시글 작성
        String sql = "insert into board (boardTitle, boardWriter, boardDivision, boardContent, boardRegDate, boardHit, boardImportant, parentNum, hierarchyNum, pEmpId, empId) " +
                " values (?, ?, ?, ?, now(), 0, ?, 0, 0, ?, ?)";
        jdbcTemplate.update(sql, board.getBoardTitle(), board.getBoardWriter(), board.getBoardDivision(), board.getBoardContent(), board.getBoardImportant(), empId, empId);

    }

    public void update(Board board){ //게시글 업데이트
        String sql = "update board set boardtitle = ?, boardcontent = ?, boarddivision = ?, boardregdate = now(), boardImportant = ? where boardnum = ?";
        jdbcTemplate.update(sql, board.getBoardTitle(), board.getBoardContent(), board.getBoardDivision(), board.getBoardImportant(), board.getBoardNum());
    }

    public void delete(Long boardNum, String sql){ //게시글 삭제
        String delete = sql;
        jdbcTemplate.update(delete, boardNum);
    }

    public void hitPlus(Long boardNum){ //조회수 증가
        String sql = "update board set boardhit = boardhit + 1 where boardnum = ?";
        jdbcTemplate.update(sql, boardNum);
    }

//    public List<Board> findLike(String sql, String param){ //게시물 제목으로 검색
//        List<Board> boards = jdbcTemplate.query(sql, (rs, n) -> {
//            Board board = new Board(
//                    rs.getLong("boardNum"),
//                    rs.getString("boardTitle"),
//                    rs.getString("boardContent"),
//                    rs.getString("boardWriter"),
//                    rs.getString("boardDivision"),
//                    rs.getTimestamp("boardRegDate").toLocalDateTime(),
//                    rs.getInt("boardHit"),
//                    rs.getInt("boardImportant"),
//                    rs.getInt("parentNum"),
//                    rs.getInt("hierarchyNum"),
//                    rs.getInt("pEmpId"),
//                    rs.getInt("empId"));
//            return board;
//
//        }, param);
//        return boards.isEmpty() ? null : boards;
//    }

    public Long maxBoardNum(){ //게시물 최대번호
        String sql = "select max(boardnum) from board";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public void parentNumUpdate(Long boardNum){ //게시물 작성시 parentnum업데이트
        String sql = "update board set parentnum = ? where boardnum = ?";
        jdbcTemplate.update(sql, boardNum, boardNum);
    }

    public void replywrite(Board pBoard, Board board, int empId){ //Q&A 답변
        String sql = "insert into board (boardTitle, boardWriter, boardDivision, boardContent, boardRegDate, boardHit, boardImportant, parentNum, hierarchyNum, pEmpId, empId) " +
                " values (?, ?, ?, ?, now(), 0, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, board.getBoardTitle(), board.getBoardWriter(), board.getBoardDivision(), board.getBoardContent(), board.getBoardImportant(), pBoard.getParentNum(), pBoard.getHierarchyNum()+1, pBoard.getPEmpId(), empId);
    }

    public int selectCount(String boardDivision){ //게시물 개수
        String sql = "select count(*) from board where boarddivision = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, boardDivision);
    }

    public int selectCount(String boardDivision, String find, String searchOption, String sql){ //게시물 개수
        if(boardDivision.equals("전체")){
            if(searchOption.equals("전체")) {
                return jdbcTemplate.queryForObject(sql, Integer.class, find, find, find);
            } else if(searchOption.equals("제목+내용")) {
                return jdbcTemplate.queryForObject(sql, Integer.class, find, find);
            }else{
                return jdbcTemplate.queryForObject(sql, Integer.class, find);
            }
        } else {
            if(searchOption.equals("전체")){
                return jdbcTemplate.queryForObject(sql, Integer.class, boardDivision, find, find, find);
            }else if(searchOption.equals("제목+내용")){
                return jdbcTemplate.queryForObject(sql, Integer.class, boardDivision, find, find);
            }else {
                return jdbcTemplate.queryForObject(sql, Integer.class, boardDivision, find);
            }
        }
    }

    public List<Board> select(int startRow, int size){ //게시물 페이징
        String sql = "select * from board order by boardnum desc limit ?, ?";
        return jdbcTemplate.query(sql, (rs, n) -> {
            Board board = new Board(
                    rs.getLong("boardNum"),
                    rs.getString("boardTitle"),
                    rs.getString("boardContent"),
                    rs.getString("boardWriter"),
                    rs.getString("boardDivision"),
                    rs.getTimestamp("boardRegDate").toLocalDateTime(),
                    rs.getInt("boardHit"),
                    rs.getInt("boardImportant"),
                    rs.getInt("parentNum"),
                    rs.getInt("hierarchyNum"),
                    rs.getInt("pEmpId"),
                    rs.getInt("empId"));
            return board;
        },startRow, size);
    }

    public List<Board> select(int startRow, int size, String sql){ //게시물 페이징
        return jdbcTemplate.query(sql, (rs, n) -> {
            Board board = new Board(
                    rs.getLong("boardNum"),
                    rs.getString("boardTitle"),
                    rs.getString("boardContent"),
                    rs.getString("boardWriter"),
                    rs.getString("boardDivision"),
                    rs.getTimestamp("boardRegDate").toLocalDateTime(),
                    rs.getInt("boardHit"),
                    rs.getInt("boardImportant"),
                    rs.getInt("parentNum"),
                    rs.getInt("hierarchyNum"),
                    rs.getInt("pEmpId"),
                    rs.getInt("empId"));
            return board;
        },startRow, size);
    }

    public List<Board> select(int startRow, int size, String sql, String find, String searchOption){ //검색 게시물 페이징
        if(searchOption.equals("전체")){
            return jdbcTemplate.query(sql, (rs, n) -> {
                Board board = new Board(
                        rs.getLong("boardNum"),
                        rs.getString("boardTitle"),
                        rs.getString("boardContent"),
                        rs.getString("boardWriter"),
                        rs.getString("boardDivision"),
                        rs.getTimestamp("boardRegDate").toLocalDateTime(),
                        rs.getInt("boardHit"),
                        rs.getInt("boardImportant"),
                        rs.getInt("parentNum"),
                        rs.getInt("hierarchyNum"),
                        rs.getInt("pEmpId"),
                        rs.getInt("empId"));
                return board;
            }, find, find, find, startRow, size);
        } else if(searchOption.equals("제목+내용")){
            return jdbcTemplate.query(sql, (rs, n) -> {
                Board board = new Board(
                        rs.getLong("boardNum"),
                        rs.getString("boardTitle"),
                        rs.getString("boardContent"),
                        rs.getString("boardWriter"),
                        rs.getString("boardDivision"),
                        rs.getTimestamp("boardRegDate").toLocalDateTime(),
                        rs.getInt("boardHit"),
                        rs.getInt("boardImportant"),
                        rs.getInt("parentNum"),
                        rs.getInt("hierarchyNum"),
                        rs.getInt("pEmpId"),
                        rs.getInt("empId"));
                return board;
            }, find, find, startRow, size);
        } else {
            return jdbcTemplate.query(sql, (rs, n) -> {
                Board board = new Board(
                        rs.getLong("boardNum"),
                        rs.getString("boardTitle"),
                        rs.getString("boardContent"),
                        rs.getString("boardWriter"),
                        rs.getString("boardDivision"),
                        rs.getTimestamp("boardRegDate").toLocalDateTime(),
                        rs.getInt("boardHit"),
                        rs.getInt("boardImportant"),
                        rs.getInt("parentNum"),
                        rs.getInt("hierarchyNum"),
                        rs.getInt("pEmpId"),
                        rs.getInt("empId"));
                return board;
            }, find, startRow, size);
        }
    }

    public Emp selectEmp(int empId){ //사원 번호로 사원 정보 가져오기
        String sql = "select * from emp where emp_id = ?";

        List<Emp> emps = jdbcTemplate.query(sql, (rs, n) -> {
            Emp emp = new Emp(
                    rs.getInt("emp_id"),
                    rs.getInt("emp_num"),
                    rs.getString("emp_password"),
                    rs.getString("emp_name"),
                    rs.getString("emp_birth"),
                    rs.getString("emp_email"),
                    rs.getString("emp_phone"),
                    rs.getDate("emp_date"),
                    rs.getInt("grade_num"),
                    rs.getInt("dept_num"));
            return emp;
        }, empId);
        return emps.isEmpty() ? null : emps.get(0);
    }

    public int maxHierarchynum (int parentNum){ //maxHierarchynum
        String sql = "select max(hierarchynum) from board where parentnum = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, parentNum);
    }
}
