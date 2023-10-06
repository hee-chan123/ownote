package com.project.ownote.board.dao;

import com.project.ownote.board.dto.Board;
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
                    rs.getInt("boardImportant"),
                    rs.getInt("parentNum"),
                    rs.getInt("hierarchyNum"),
                    rs.getInt("boardHit"));
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
                    rs.getInt("boardImportant"),
                    rs.getInt("parentNum"),
                    rs.getInt("hierarchyNum"),
                    rs.getInt("boardHit"));
            return board;

        }, boardNum) ;
        return boards.isEmpty() ? null : boards.get(0);
    }
    public void write(Board board){ //게시글 작성
        String sql = "insert into board (boardTitle, boardWriter, boardDivision, boardContent, boardRegDate, boardImportant, parentNum, hierarchyNum, boardHit) " +
                " values (?, ?, ?, ?, now(), ?, 0, 0, 0)";
        jdbcTemplate.update(sql, board.getBoardTitle(), board.getBoardWriter(), board.getBoardDivision(), board.getBoardContent(), board.getBoardImportant());

    }

    public void update(Board board){ //게시글 업데이트
        String sql = "update board set boardtitle = ?, boardcontent = ?, boarddivision = ?, boardregdate = now(), boardImportant = ? where boardnum = ?";
        jdbcTemplate.update(sql, board.getBoardTitle(), board.getBoardContent(), board.getBoardDivision(), board.getBoardImportant(), board.getBoardNum());
    }

    public void delete(Long boardNum){ //게시글 삭제
        String sql = "delete from board where boardnum = ?";
        jdbcTemplate.update(sql, boardNum);
    }

    public void hitPlus(Long boardNum){ //조회수 증가
        String sql = "update board set boardhit = boardhit + 1 where boardnum = ?";
        jdbcTemplate.update(sql, boardNum);
    }

    public List<Board> findLike(String boardDivision, String find){ //게시물 제목으로 검색
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

        List<Board> boards = jdbcTemplate.query(sql, (rs, n) -> {
            Board board = new Board(
                    rs.getLong("boardNum"),
                    rs.getString("boardTitle"),
                    rs.getString("boardContent"),
                    rs.getString("boardWriter"),
                    rs.getString("boardDivision"),
                    rs.getTimestamp("boardRegDate").toLocalDateTime(),
                    rs.getInt("boardImportant"),
                    rs.getInt("parentNum"),
                    rs.getInt("hierarchyNum"),
                    rs.getInt("boardHit"));
            return board;

        }, param);
        return boards.isEmpty() ? null : boards;
    }

    public Long maxBoardNum(){ //게시물 최대번호
        String sql = "select max(boardnum) from board";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public void parentNumUpdate(Long boardNum){ //게시물 작성시 parentnum업데이트
        String sql = "update board set parentnum = ? where boardnum = ?";
        jdbcTemplate.update(sql, boardNum, boardNum);
    }

    public void replywrite(Board pBoard, Board board){ //Q&A 답변
        String sql = "insert into board (boardTitle, boardWriter, boardDivision, boardContent, boardRegDate, boardImportant, parentNum, hierarchyNum, boardHit) " +
                " values (?, ?, ?, ?, now(), ?, ?, ?, 0)";
        jdbcTemplate.update(sql, board.getBoardTitle(), board.getBoardWriter(), board.getBoardDivision(), board.getBoardContent(), board.getBoardImportant(), pBoard.getParentNum(), pBoard.getHierarchyNum()+1);
    }

    public int selectCount(String boardDivision){ //게시물 갯수
        String sql = "select count(*) from board where boarddivision = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, boardDivision);
    }

    public int selectCount(String boardDivision, String find){ //게시물 갯수
        String sql = "select count(*) from board where boardDivision = ? and boardtitle like ? order by boardnum desc";
        String f = "%" + find + "%";
        return jdbcTemplate.queryForObject(sql, Integer.class, boardDivision, f);
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
                    rs.getInt("boardImportant"),
                    rs.getInt("parentNum"),
                    rs.getInt("hierarchyNum"),
                    rs.getInt("boardHit"));
            return board;
        },startRow, size);
    }

    public List<Board> select(int startRow, int size, String boardDivision){ //게시물 페이징
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

        return jdbcTemplate.query(sql, (rs, n) -> {
            Board board = new Board(
                    rs.getLong("boardNum"),
                    rs.getString("boardTitle"),
                    rs.getString("boardContent"),
                    rs.getString("boardWriter"),
                    rs.getString("boardDivision"),
                    rs.getTimestamp("boardRegDate").toLocalDateTime(),
                    rs.getInt("boardImportant"),
                    rs.getInt("parentNum"),
                    rs.getInt("hierarchyNum"),
                    rs.getInt("boardHit"));
            return board;
        },startRow, size);
    }

    public List<Board> select(int startRow, int size, String boardDivision, String find){ //검색 게시물 페이징
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

        return jdbcTemplate.query(sql, (rs, n) -> {
            Board board = new Board(
                    rs.getLong("boardNum"),
                    rs.getString("boardTitle"),
                    rs.getString("boardContent"),
                    rs.getString("boardWriter"),
                    rs.getString("boardDivision"),
                    rs.getTimestamp("boardRegDate").toLocalDateTime(),
                    rs.getInt("boardImportant"),
                    rs.getInt("parentNum"),
                    rs.getInt("hierarchyNum"),
                    rs.getInt("boardHit"));
            return board;
        }, f, startRow, size);
    }
}
