package com.project.ownote.Controller;

import com.project.ownote.board.dto.Board;
import com.project.ownote.board.service.BoardService;
import com.project.ownote.emp.login.dto.AuthInfo;
import com.project.ownote.musicContest.dto.MusicContestDto;
import com.project.ownote.musicContest.service.MusicContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    MusicContestService musicContestService;

    @Autowired
    BoardService boardService;

    @GetMapping("/")
    public String main(HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        List<MusicContestDto> contestList = musicContestService.selectAllMusicContest();
        List<Board> boardNotice = boardService.select2(0, 7, "회사뉴스및공지");
        model.addAttribute("boardNotice", boardNotice);
        model.addAttribute("authInfo", authInfo);
        model.addAttribute("contestList", contestList);
        return "ownote";
    }

    @GetMapping("/ownote")
    public String ownote() {return "ownote";}

}
