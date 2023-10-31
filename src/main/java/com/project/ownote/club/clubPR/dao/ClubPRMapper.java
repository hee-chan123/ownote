package com.project.ownote.club.clubPR.dao;

import com.project.ownote.club.clubPR.dto.ClubBoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

@Mapper
public interface ClubPRMapper {

    void insertClubBoard(ClubBoardDto clubBoardDto);

    Integer countClubBoard();

    List<ClubBoardDto> clubListPage(@Param("startRow") int startRow, @Param("size") int size);

    List<ClubBoardDto> selectAllClubBoard();

    ClubBoardDto selectOneClubBoard(@Param("clubboard_id") int clubboard_id);

    void modifyClubBoard(ClubBoardDto clubBoardDto);

    int deleteClubBoard(@Param("clubboard_id") int clubboard_id);
}
