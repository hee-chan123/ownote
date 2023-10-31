package com.project.ownote.club.clubInfo.dto;

import com.project.ownote.club.clubPR.dto.ClubBoardDto;

import java.util.List;

public class ClubPage {
    private int total;
    private int currentPage;
    private List<ClubDto> clubDtos;
    private int totalPages;
    private int startPage;
    private int endPage;

    public ClubPage(int total, int currentPage, int size, List<ClubDto> clubDtos) {
        // 총 항목 수, 현재 페이지 번호, 페이지당 항목 수, 사용자 목록을 받아와서 초기화합니다.
        this.total = total;
        this.currentPage = currentPage;
        this.clubDtos = clubDtos;

        // 만약 총 항목 수가 0이라면, 페이지 관련 변수를 모두 0으로 설정합니다.
        if (total == 0) {
            totalPages = 0;
            startPage = 0;
            endPage = 0;
        } else {
            // 총 페이지 수를 계산합니다.
            totalPages = total / size;
            if (total % size > 0) {
                totalPages++;
            }

            // 현재 페이지를 기준으로 시작 페이지와 끝 페이지를 계산합니다.
            int modVal = currentPage % 5;
            startPage = currentPage / 5 * 5 + 1;
            if (modVal == 0)
                startPage -= 5;

            endPage = startPage + 4;
            if (endPage > totalPages)
                endPage = totalPages;
        }
    }

    public int getTotal() {
        return total;
    }

    public boolean hasNoClubDtos() {
        return total == 0;
    }

    public boolean hasClubDtos() {
        return total > 0;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<ClubDto> getClubDtos() {
        return clubDtos;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }
}

