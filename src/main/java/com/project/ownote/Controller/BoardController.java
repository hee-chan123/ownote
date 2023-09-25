package com.project.ownote.Controller;

import com.project.ownote.notice.Board;
import com.project.ownote.notice.BoardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    BoardDao boardDao;

    @GetMapping("/board/boardmain")
    public String noticeMain(Model model){
        List<Board> boardList = boardDao.selectAll();
        model.addAttribute("boardList", boardList);
        return "board/boardMain";
    }

    @GetMapping("/board/noticeList")
    public String notice(Model model){
        List<Board> boardList = boardDao.selectAll();
        model.addAttribute("boardList", boardList);
        return "noticeList";
    }

    @GetMapping("/board/boardView/{boardNum}")
    public String View(@PathVariable Long boardNum, Model model){
        Board board = boardDao.selectByNum(boardNum);
        model.addAttribute("board", board);
        return "board/boardView";
    }

    @GetMapping("/board/boardwriteform")
    public String noticeWriteForm(){
        return "board/boardWrite";
    }

    @PostMapping("/board/boardwrite")
    public String noticeWrite(Model model, @ModelAttribute("board") Board board){
        boardDao.write(board);
        return "redirect:/board/boardmain";
    }

    @GetMapping("/board/boardupdate/{boardNum}")
    public String noticeUpdateForm(@PathVariable Long boardNum, Model model){
        Board board = boardDao.selectByNum(boardNum);
        model.addAttribute("board", board);
        return "board/boardUpdate";
    }

    @PostMapping("/board/boardupdate/{boardNum}")
    public String noticeUpdate(@PathVariable Long boardNum, @ModelAttribute("board") Board board){
        boardDao.update(board);
        return "redirect:/board/boardmain";
    }

    @GetMapping("/board/boarddelete/{boardNum}")
    public String noticeDelete(@PathVariable Long boardNum){
        boardDao.delete(boardNum);
        return "redirect:/board/boardmain";
    }
}
