package com.project.ownote.musicContest.service;

import com.project.ownote.musicContest.dao.MusicContestMapper;
import com.project.ownote.musicContest.dto.LikeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    MusicContestMapper musicContestMapper;

    public void increaseLike(int musiccontest_id){
        musicContestMapper.increaseLike(musiccontest_id);
    }

    public void insertLike(LikeDto likeDto) {
        musicContestMapper.insertLike(likeDto);
    }

    public LikeDto selectLike(int musiccontest_id){
        return musicContestMapper.selectLike(musiccontest_id);
    }

    public LikeDto selectLikeEmp(int emp_id) {
        return musicContestMapper.selectLikeEmp(emp_id);
    };
}
