package com.project.ownote.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Board> selectAll(){
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
                    rs.getInt("boardHit"));
            return board;
        });
        return list;
    }

    public List<Board> selectAllOrder(){
        String sql = "select * from board order by boardimportant desc, boardnum desc ";
        List<Board> list = jdbcTemplate.query(sql, (rs, n) ->{
            Board board = new Board(
                    rs.getLong("boardNum"),
                    rs.getString("boardTitle"),
                    rs.getString("boardContent"),
                    rs.getString("boardWriter"),
                    rs.getString("boardDivision"),
                    rs.getTimestamp("boardRegDate").toLocalDateTime(),
                    rs.getInt("boardImportant"),
                    rs.getInt("boardHit"));
            return board;
        });
        return list;
    }

    public Board selectByNum(Long boardNum){
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
                    rs.getInt("boardHit"));
            return board;

        }, boardNum) ;
        return boards.isEmpty() ? null : boards.get(0);
    }
    public void write(Board board){
        String sql = "insert into board (boardTitle, boardWriter, boardDivision, boardContent, boardRegDate, boardImportant, boardHit) " +
                " values (?, ?, ?, ?, now(), ?, 0)";
        jdbcTemplate.update(sql, board.getBoardTitle(), board.getBoardWriter(), board.getBoardDivision(), board.getBoardContent(), board.getBoardImportant());

    }

    public void update(Board board){
        String sql = "update board set boardtitle = ?, boardcontent = ?, boarddivision = ?, boardregdate = now(), boardImportant = ? where boardnum = ?";
        jdbcTemplate.update(sql, board.getBoardTitle(), board.getBoardContent(), board.getBoardDivision(), board.getBoardNum(), board.getBoardImportant());
    }

    public void delete(Long boardNum){
        String sql = "delete from board where boardnum = ?";
        jdbcTemplate.update(sql, boardNum);
    }

    public void hitPlus(Long boardNum){
        String sql = "update board set boardhit = boardhit + 1 where boardnum = ?";
        jdbcTemplate.update(sql, boardNum);
    }
}
