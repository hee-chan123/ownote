package com.project.ownote.musicContest.controller;

import com.project.ownote.emp.login.dto.AuthInfo;
import com.project.ownote.musicContest.dto.LikeDto;
import com.project.ownote.musicContest.dto.MusicContestDto;
import com.project.ownote.musicContest.service.LikeService;
import com.project.ownote.musicContest.service.MusicContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MusicContestController {
    @Autowired
    MusicContestService musicContestService;
    @Autowired
    LikeService likeService;

    @GetMapping("/musicContest/list")
    public String list(Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        List<MusicContestDto> contestList = musicContestService.selectAllMusicContest();
        System.out.println(contestList);
        model.addAttribute("contestList", contestList);
        model.addAttribute("authInfo", authInfo);
        return "musicContest/musicContest_list";
    }

    @GetMapping("/musicContest/write")
    public String insertMusicContestG(HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        return "musicContest/musicContestWriteForm";
    }

    @PostMapping("/musicContest/list")
    public String insertMusicContest(MusicContestDto musicContestDto){
        musicContestService.insertMusicContest(musicContestDto);
        return "redirect:/musicContest/list";
    }

    @GetMapping("/musicContest/view")
    public String gView(@RequestParam(value = "musiccontest_id") int musiccontest_id,
                        Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        MusicContestDto dto = musicContestService.selectOneMusicContest(musiccontest_id);
        model.addAttribute("authInfo", authInfo);
        model.addAttribute("dto", dto);
        return "musicContest/musicContest_view";
    }

    @GetMapping("/musicContest/delete")
    public String deleteMusicContest(@RequestParam("musiccontest_id") int musiccontest_id){
        musicContestService.deleteMusicContest(musiccontest_id);
        return "redirect:/musicContest/list";
    }

    @PostMapping("/musicContest/like")
    @Transactional
    public String getIncreaseLike(@RequestParam(value = "musiccontest_id") int musiccontest_id,
                                  HttpSession session, Model model, LikeDto likeDto){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        LikeDto dto = likeService.selectLike(musiccontest_id);
        System.out.println("-----------------------------------------"+dto);
        likeDto.setEmp_id(authInfo.getEmp_id());
        System.out.println("987987987897987989"+likeDto);
        LikeDto likeEmpDto = likeService.selectLikeEmp(authInfo.getEmp_id());

        if (likeEmpDto == null) {
            likeService.increaseLike(musiccontest_id);
            likeService.insertLike(likeDto);
            model.addAttribute("dto", dto);
            model.addAttribute("authInfo", authInfo);
            return "redirect:/musicContest/list";
        } else {
            return "redirect:/musicContest/list?error=1";
        }
    }
}
