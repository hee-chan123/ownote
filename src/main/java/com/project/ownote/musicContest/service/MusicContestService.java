package com.project.ownote.musicContest.service;

import com.project.ownote.musicContest.dao.MusicContestMapper;
import com.project.ownote.musicContest.dto.LikeDto;
import com.project.ownote.musicContest.dto.MusicContestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicContestService {
    @Autowired
    MusicContestMapper musicContestMapper;

    public void insertMusicContest(MusicContestDto musicContestDto) {
        musicContestMapper.insertMusicContest(musicContestDto);
    }

    public List<MusicContestDto> selectAllMusicContest() {
        return musicContestMapper.selectAllMusicContest();
    }

    public MusicContestDto selectOneMusicContest(int musiccontest_id) {
        return musicContestMapper.selectOneMusicContest(musiccontest_id);
    }

    public Integer deleteMusicContest(int musiccontest_id) {
        return musicContestMapper.deleteMusicContest(musiccontest_id);
    }


}
