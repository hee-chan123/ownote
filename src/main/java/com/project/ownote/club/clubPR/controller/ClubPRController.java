package com.project.ownote.club.clubPR.controller;

import com.github.pagehelper.PageInfo;
import com.project.ownote.club.clubPR.dto.ClubBoardDto;
import com.project.ownote.club.clubPR.dto.ClubBoardPage;
import com.project.ownote.club.clubPR.service.ClubPRService;
import com.project.ownote.emp.login.dto.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.PublicKey;
import java.util.List;

@Controller
@Slf4j
public class ClubPRController {

    @Autowired
    private ClubPRService clubPRService;
    private int size = 4;

    // 페이지당 회원 수를 기준으로 사용자 페이지 정보를 반환하는 메서드
    public ClubBoardPage getClubBoardPage(int pageNum, int pageSize) {
        int total = clubPRService.countClubBoard(); // 전체 사용자 수 조회
        List<ClubBoardDto> clubBoardDto = clubPRService.listPage((pageNum - 1) * size, size); // 페이지에 해당하는 사용자 목록 조회
        return new ClubBoardPage(total, pageNum, size, clubBoardDto); // 사용자 페이지 정보를 생성하여 반환
    }

    @GetMapping("club/list")
    public String list(Model model, @RequestParam(name = "pageNo", required = false) String pageNo, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
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
        ClubBoardPage clubBoardPage = getClubBoardPage(pageNum, pageSize); // 사용자 페이지 정보를 조회

        model.addAttribute("listPage", clubBoardPage); // 모델에 사용자 페이지 정보를 추가
        return "/club/club_list";
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

    @GetMapping("/club/write")
    public String boardWriteG(HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo)session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);

        return "/club/club_write";

    }

    @PostMapping("/club/write")
    public String boardWriteP(ClubBoardDto clubBoardDto, Model model, MultipartFile file, HttpSession session) throws Exception{


        clubPRService.insertClubBoard(clubBoardDto, file);
        return "redirect:/club/list";
    }

    @GetMapping("/club/view")
    public String view(@RequestParam(value = "clubboard_id") int clubboard_id, Model model, HttpSession session) {
        AuthInfo authInfo = (AuthInfo)session.getAttribute("authInfo");
        ClubBoardDto dto = clubPRService.selectOneClubBoard(clubboard_id);
        model.addAttribute("authInfo", authInfo);
        model.addAttribute("dto", dto);
        return "/club/club_view";

    }

    @GetMapping("/club/modify")
    public String modifyBoardG(@RequestParam(value = "clubboard_id") int clubboard_id,
                              Model model, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        ClubBoardDto dto = clubPRService.selectOneClubBoard(clubboard_id);
        model.addAttribute("dto", dto);

        return "/club/club_modify";
    }

    @PostMapping("/club/modify")
    public String modifyBoardP(@RequestParam("clubboard_id") int clubboard_id, ClubBoardDto clubBoardDto,
                               Model model, MultipartFile file, HttpSession session) throws Exception{

        if(clubBoardDto.getClubboard_filename() == null && clubBoardDto.getClubboard_filepath() == null){
            ClubBoardDto clubBoardDto1 =  clubPRService.selectOneClubBoard(clubboard_id);
            clubBoardDto.setClubboard_filename(clubBoardDto1.getClubboard_filename());
            clubBoardDto.setClubboard_filepath(clubBoardDto1.getClubboard_filepath());
        }
        AuthInfo authInfo = (AuthInfo)session.getAttribute("authInfo");
        ClubBoardDto dto = clubPRService.selectOneClubBoard(clubboard_id);
        model.addAttribute("authInfo", authInfo);
        model.addAttribute("dto", dto);
        clubPRService.modifyClubBoard(clubBoardDto, file);
        return "/club/club_view";
    }

    @GetMapping("/club/delete")
    @ResponseBody
    public String deleteBoardP(@RequestParam("clubboardId") int clubboardId) {
        System.out.println(clubboardId);
        clubPRService.deleteClubBoard(clubboardId);
        return "/club/list";
    }



}
