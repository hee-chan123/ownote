package com.project.ownote.club.clubInfo.dao;

import com.project.ownote.club.clubInfo.dto.ClubDto;
import com.project.ownote.club.clubPR.dto.ClubBoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClubInfoMapper {

    void insertClub(ClubDto clubDto);

    Integer countClub();

    List<ClubDto> clubInfoListPage(@Param("startRow") int startRow, @Param("size") int size);

    List<ClubDto> selectAllClub();

    ClubDto selectOneClub(@Param("club_id") int club_id);

    void modifyClub(ClubDto clubDto);

    int deleteClub(@Param("club_id") int club_id);
}
