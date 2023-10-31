package com.project.ownote.club.clubInfo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.ownote.club.clubInfo.dao.ClubInfoMapper;
import com.project.ownote.club.clubInfo.dto.ClubDto;
import com.project.ownote.club.clubPR.dao.ClubPRMapper;
import com.project.ownote.club.clubPR.dto.ClubBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class ClubInfoService {

    @Autowired
    private ClubInfoMapper clubInfoMapper;

    public void insertClub(ClubDto clubDto){


        clubInfoMapper.insertClub(clubDto);
    }

    public Integer countClub(){
        return clubInfoMapper.countClub();
    };

    public List<ClubDto> listPage(int startRow, int size) {
        return clubInfoMapper.clubInfoListPage(startRow, size);
    }

    public ClubDto selectOneClub(int club_id) {
        return clubInfoMapper.selectOneClub(club_id);
    }

    public void modifyClub(ClubDto clubDto){


        clubInfoMapper.modifyClub(clubDto);
    }


    public Integer deleteClub(int club_id) {
        ClubDto dto = selectOneClub(club_id);
        return clubInfoMapper.deleteClub(club_id);
    }


}
