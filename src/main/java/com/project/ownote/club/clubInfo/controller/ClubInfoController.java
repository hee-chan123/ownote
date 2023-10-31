package com.project.ownote.club.clubInfo.controller;

import com.project.ownote.club.clubInfo.dto.ClubDto;
import com.project.ownote.club.clubInfo.dto.ClubPage;
import com.project.ownote.club.clubInfo.service.ClubInfoService;
import com.project.ownote.club.clubPR.dto.ClubBoardDto;
import com.project.ownote.club.clubPR.dto.ClubBoardPage;
import com.project.ownote.club.clubPR.service.ClubPRService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Slf4j
public class ClubInfoController {

    @Autowired
    private ClubInfoService clubInfoService;
    private int size = 4;

    // 페이지당 회원 수를 기준으로 사용자 페이지 정보를 반환하는 메서드
    public ClubPage getClubPage(int pageNum, int pageSize) {
        int total = clubInfoService.countClub(); // 전체 사용자 수 조회
        List<ClubDto> clubDto = clubInfoService.listPage((pageNum - 1) * size, size); // 페이지에 해당하는 사용자 목록 조회
        return new ClubPage(total, pageNum, size, clubDto); // 사용자 페이지 정보를 생성하여 반환
    }

    @GetMapping("clubInfo/list")
    public String list(Model model, @RequestParam(name = "pageNo", required = false) String pageNo) {
        int pageSize = 4; // 페이지 크기 설정 (한 페이지에 보여줄 회원 수)
        int pageNum = 1;
        try {
            if (pageNo != null) {
                pageNum = Integer.parseInt(pageNo);
            }
        } catch (NumberFormatException e) {
            // 페이지 번호가 숫자로 변환되지 않을 때 처리할 코드 추가
            // 예를 들어, 기본 페이지 번호를 설정하거나 에러 핸들링을 수행합니다.
            pageNum = 1; // 기본 페이지 번호 설정
        }
//		  if (pageNo != null) {
//	            pageNum = Integer.parseInt(pageNo);
//	        }
        System.out.println(pageNo);
        ClubPage clubPage = getClubPage(pageNum, pageSize); // 사용자 페이지 정보를 조회

        model.addAttribute("listPage", clubPage); // 모델에 사용자 페이지 정보를 추가
        return "/clubinfo/clubinfo_list";
    }

/*    @GetMapping("/club/list")
    public String boardListG( @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(name = "pageSize", defaultValue = "4") int pageSize,
                              Model model) {
        PageInfo<ClubBoardDto> pageInfo = clubPRService.selectAllClubBoard(pageNum, pageSize);
        System.out.println(pageInfo);

        model.addAttribute("pageInfo", pageInfo);
        return "/club/club_list"; //

    }*/

    @GetMapping("/clubInfo/write")
    public String clubWriteG() {
        return "/clubInfo/club_write";
    }

    @PostMapping("/clubInfo/write")
    public String clubWriteP(ClubDto clubDto, Model model) {


        clubInfoService.insertClub(clubDto);
        return "redirect:/clubInfo/list";
    }

    @GetMapping("/clubInfo/view")
    public String view(@RequestParam(value = "club_id") int club_id, Model model) {

            ClubDto dto = clubInfoService.selectOneClub(club_id);

            model.addAttribute("dto", dto);
            return "/clubInfo/club_view";

    }

    @GetMapping("/clubInfo/modify")
    public String modifyClubG(@RequestParam(value = "club_id") int club_id,
                              Model model) {

        ClubDto dto = clubInfoService.selectOneClub(club_id);
        model.addAttribute("dto", dto);

        return "/clubInfo/club_modify";
    }

    @PostMapping("/clubInfo/modify")
    public String modifyBoardP(@RequestParam("club_id") int club_id, ClubDto clubDto,
                               Model model) {

        clubInfoService.modifyClub(clubDto);
        return "redirect:/clubInfo/list";
    }

    @GetMapping("/clubInfo/delete")
    @ResponseBody
    public String deleteBoardP(@RequestParam("clubId") int clubId) {
        System.out.println(clubId);
        clubInfoService.deleteClub(clubId);
        return "/clubInfo/list";
    }



}
