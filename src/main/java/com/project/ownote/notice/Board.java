package com.project.ownote.notice;

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
    private int boardImportant;
    private int boardHit;

    public Board(String boardTitle, String boardContent, String boardWriter, String boardDivision, int boardImportant) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardWriter = boardWriter;
        this.boardDivision = boardDivision;
        this.boardImportant = boardImportant;
    }
}
