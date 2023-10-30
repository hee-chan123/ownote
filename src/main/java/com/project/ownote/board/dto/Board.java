package com.project.ownote.board.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Board {

    private Long boardnum;
    private String boardtitle;
    private String boardcontent;
    private String boardwriter;
    private String boarddivision;
    private Date boardregdate;
    private int boardhit;
    private int boardimportant;
    private int parentnum;
    private int hierarchynum;
    private int pempid;
    private int empid;


    public Board(String boardtitle, String boardcontent, String boardwriter, String boarddivision, int boardimportant, int parentnum, int hierarchynum, int pempid, int empid) {
        this.boardtitle = boardtitle;
        this.boardcontent = boardcontent;
        this.boardwriter = boardwriter;
        this.boarddivision = boarddivision;
        this.boardimportant = boardimportant;
        this.parentnum = parentnum;
        this.hierarchynum = hierarchynum;
        this.pempid = pempid;
        this.empid = empid;
    }
}
