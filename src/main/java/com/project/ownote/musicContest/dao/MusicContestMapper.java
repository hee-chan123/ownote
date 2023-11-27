package com.project.ownote.musicContest.dao;

import com.project.ownote.musicContest.dto.LikeDto;
import com.project.ownote.musicContest.dto.MusicContestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MusicContestMapper {
    void insertMusicContest(MusicContestDto musicContestDto);
    List<MusicContestDto> selectAllMusicContest();
    MusicContestDto selectOneMusicContest(@Param("musiccontest_id") int musiccontest_id);
    int deleteMusicContest(@Param("musiccontest_id") int musiccontest_id);
    void increaseLike(@Param("musiccontest_id") int musiccontest_id);
    void insertLike(LikeDto likeDto);
    LikeDto selectLike(@Param("musiccontest_id") int musiccontest_id);

    LikeDto selectLikeEmp(@Param("emp_id") int emp_id);
}
