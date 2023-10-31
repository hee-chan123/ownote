package com.project.ownote.club.clubPR.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.ownote.club.clubPR.dao.ClubPRMapper;
import com.project.ownote.club.clubPR.dto.ClubBoardDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class ClubPRService {

    @Autowired
    private ClubPRMapper clubPRMapper;

    public void insertClubBoard(ClubBoardDto clubBoardDto, MultipartFile file)throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
//
//        UUID uuid = UUID.randomUUID();
//
//        String filename = uuid + "_" + file.getOriginalFilename();
//
//        File savefile = new File(projectPath, filename);
//
//        file.transferTo(savefile);
//
//        clubBoardDto.setClubboard_filename(filename);
//        clubBoardDto.setClubboard_filepath("/files/" + filename);

        // 파일이 업로드되었는지 확인
        if (file != null && !file.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String filename = uuid + "_" + file.getOriginalFilename();
            File savefile = new File(projectPath, filename);
            file.transferTo(savefile);

            clubBoardDto.setClubboard_filename(filename);
            clubBoardDto.setClubboard_filepath("/files/" + filename);
        } else {
            // 파일이 업로드되지 않았을 때, filename과 filepath를 null로 설정
            clubBoardDto.setClubboard_filename(null);
            clubBoardDto.setClubboard_filepath(null);
        }

        clubPRMapper.insertClubBoard(clubBoardDto);
    }

    public PageInfo<ClubBoardDto> selectAllClubBoard(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ClubBoardDto> list = clubPRMapper.selectAllClubBoard();
        return new PageInfo<>(list);
    }

    public Integer countClubBoard(){
        return clubPRMapper.countClubBoard();
    };

    public List<ClubBoardDto> listPage(int startRow, int size) {
        return clubPRMapper.clubListPage(startRow, size);
    }

    public ClubBoardDto selectOneClubBoard(int clubboard_id) {
        return clubPRMapper.selectOneClubBoard(clubboard_id);
    }

    public void modifyClubBoard(ClubBoardDto clubBoardDto, MultipartFile file)throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\modifyfiles";
        String originProjectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";
        // 파일이 업로드되었는지 확인
        if (file != null && !file.isEmpty()) {

            File fileToDelete = new File(originProjectPath + clubBoardDto.getClubboard_filepath());
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }

            UUID uuid = UUID.randomUUID();
            String filename = uuid + "_" + file.getOriginalFilename();
            File savefile = new File(projectPath, filename);
            file.transferTo(savefile);

            clubBoardDto.setClubboard_filename(filename);
            clubBoardDto.setClubboard_filepath("/modifyfiles/" + filename);
        } else {
            // 파일이 업로드되지 않았을 때, filename과 filepath를 null로 설정

            clubBoardDto.setClubboard_filename(clubBoardDto.getClubboard_filename());
            clubBoardDto.setClubboard_filepath(clubBoardDto.getClubboard_filepath());
        }

        clubPRMapper.modifyClubBoard(clubBoardDto);
    }


    public Integer deleteClubBoard(int clubboard_id) {
        ClubBoardDto dto = selectOneClubBoard(clubboard_id);
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";

        File fileToDelete = new File(projectPath + dto.getClubboard_filepath());
        System.out.println(fileToDelete);
        if (fileToDelete.exists()) {
            fileToDelete.delete();

        }
        return clubPRMapper.deleteClubBoard(clubboard_id);
    }


}
