package com.project.ownote.board.page;
import com.project.ownote.board.dto.Board;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardPage {

    private int total;
    private int currentPage;
    private List<Board> content;
    private int totalPages;
    private int startPage;
    private int endPage;

    public BoardPage(int total, int currentPage, int size, List<Board> content) {
        this.total = total;
        this.currentPage = currentPage;
        this.content = content;
        if (total == 0) {
            totalPages = 0;
            startPage = 0;
            endPage = 0;
        } else {
            totalPages = total / size;
            if (total % size > 0) {
                totalPages++;
            }
            int modVal = currentPage % 5;
            startPage = currentPage / 5 * 5 + 1;
            if (modVal == 0) startPage -= 5;

            endPage = startPage + 4;
            if (endPage > totalPages) endPage = totalPages;
        }
    }


    public boolean hasNoArticles() {
        return total == 0;
    }

    public boolean hasArticles() {
        return total > 0;
    }
}