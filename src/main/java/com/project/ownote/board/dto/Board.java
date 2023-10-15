package com.project.ownote.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Board {

    private Long boardNum;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private String boardDivision;
    private LocalDateTime boardRegDate;
    private int boardHit;
    private int boardImportant;
    private int parentNum;
    private int hierarchyNum;
    private int pEmpId;
    private int empId;


    public Board(String boardTitle, String boardContent, String boardWriter, String boardDivision, int boardImportant, int parentNum, int hierarchyNum, int pEmpId, int empId) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardWriter = boardWriter;
        this.boardDivision = boardDivision;
        this.boardImportant = boardImportant;
        this.parentNum = parentNum;
        this.hierarchyNum = hierarchyNum;
        this.pEmpId = pEmpId;
        this.empId = empId;
    }
}
