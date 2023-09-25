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

    @GetMapping("/board/boardmain") //게시판 메인
    public String boardMain(Model model){
        List<Board> boardList = boardDao.selectAll();
        model.addAttribute("boardList", boardList);
        return "board/boardMain";
    }

    @GetMapping("/board/boardView/{boardNum}") //게시판 뷰
    public String View(@PathVariable Long boardNum, Model model){
        Board board = boardDao.selectByNum(boardNum);
        boardDao.hitPlus(boardNum);
        model.addAttribute("board", board);
        return "board/boardView";
    }

    @GetMapping("/board/boardwriteform") //게시판 글 쓰기 폼
    public String noticeWriteForm(){
        return "board/boardWrite";
    }

    @PostMapping("/board/boardwrite") //게시판 글 쓰기
    public String noticeWrite(Model model, @ModelAttribute("board") Board board){
        boardDao.write(board);
        return "redirect:/board/boardmain";
    }

    @GetMapping("/board/boardupdate/{boardNum}") //게시판 업데이트 폼
    public String noticeUpdateForm(@PathVariable Long boardNum, Model model){
        Board board = boardDao.selectByNum(boardNum);
        model.addAttribute("board", board);
        return "board/boardUpdate";
    }

    @PostMapping("/board/boardupdate/{boardNum}") //게시판 업데이트
    public String noticeUpdate(@PathVariable Long boardNum, @ModelAttribute("board") Board board){
        boardDao.update(board);
        return "redirect:/board/boardmain";
    }

    @GetMapping("/board/boarddelete/{boardNum}") //게시판 삭제
    public String noticeDelete(@PathVariable Long boardNum){
        boardDao.delete(boardNum);
        return "redirect:/board/boardmain";
    }

    @GetMapping("/board/noticeList") //공지사항 이동
    public String notice(Model model){
        List<Board> boardList = boardDao.selectAll();
        model.addAttribute("boardList", boardList);
        return "board/noticeList";
    }

    @GetMapping("/board/forumList") //자유게시판 이동
    public String forum(Model model){
        List<Board> boardList = boardDao.selectAll();
        model.addAttribute("boardList", boardList);
        return "board/forumList";
    }

    @GetMapping("/board/qaList") //Q&A 이동
    public String qa(Model model){
        List<Board> boardList = boardDao.selectAll();
        model.addAttribute("boardList", boardList);
        return "board/qaList";
    }
}
