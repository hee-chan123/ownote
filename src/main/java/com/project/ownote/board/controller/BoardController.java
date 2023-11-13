package com.project.ownote.board.controller;

import com.project.ownote.board.dto.Board;
import com.project.ownote.board.page.BoardPage;
import com.project.ownote.board.page.ListBoard;
import com.project.ownote.board.service.BoardService;
import com.project.ownote.emp.login.dto.AuthInfo;
import com.project.ownote.emp.login.dto.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ListBoard listBoard;

    @GetMapping("/board/boardmain") //게시판 메인
    public String boardMain(Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        List<Board> boardNotice = boardService.select2(0, 7, "회사뉴스및공지");
        List<Board> boardForum = boardService.select2(0, 7, "자유게시판");
        List<Board> boardQa = boardService.select2(0, 7, "사내시스템/F&Q");
        Emp emp = boardService.selectEmp(authInfo.getEmp_id());

        model.addAttribute("boardNotice", boardNotice);
        model.addAttribute("boardForum", boardForum);
        model.addAttribute("boardQa", boardQa);
        model.addAttribute("authInfo", authInfo);
        model.addAttribute("emp", emp);
        return "board/boardmain";
    }

    @GetMapping("/board/boardview/{boardNum}") //게시판 뷰
    public String View(@PathVariable Long boardNum, Model model, HttpSession session, @RequestParam(value = "mainCheck", required = false) String mainCheck){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardService.selectEmp(empId);
        Board board = boardService.selectByNum(boardNum);
        boardService.hitPlus(boardNum);
        int maxHierarchyNum = boardService.maxHierarchyNum(board.getParentNum());

        if(mainCheck != null){
            model.addAttribute("mainCheck", mainCheck);
        }

        model.addAttribute("emp", emp);
        model.addAttribute("board", board);
        model.addAttribute("maxHierarchyNum", maxHierarchyNum);
        model.addAttribute("authInfo", authInfo);
        return "board/boardview";
    }

    @GetMapping("/board/boardwriteform") //게시판 글 쓰기 폼
    public String WriteForm(Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardService.selectEmp(empId);

        model.addAttribute("emp", emp);
        model.addAttribute("authInfo", authInfo);
        return "board/boardwrite";
    }

    @PostMapping("/board/boardwrite") //게시판 글 쓰기
    public String Write(@ModelAttribute("board") Board board, Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Emp emp = boardService.selectEmp(authInfo.getEmp_id());
        int empId = authInfo.getEmp_id();

        boardService.write(board, empId);
        boardService.parentNumUpdate(boardService.maxBoardNum());

        model.addAttribute("authInfo", authInfo);
        model.addAttribute("emp", emp);

        switch (board.getBoardDivision()) {
            case "회사뉴스및공지":
                return "redirect:/board/noticelist";
            case "자유게시판":
                return "redirect:/board/forumlist";
            case "사내시스템/F&Q":
                return "redirect:/board/qalist";
            default:
                return "redirect:/board/boardmain";
        }
    }

    @GetMapping("/board/boardupdate/{boardNum}") //게시판 업데이트 폼
    public String noticeUpdateForm(@PathVariable Long boardNum, Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardService.selectEmp(empId);
        Board board = boardService.selectByNum(boardNum);

        model.addAttribute("emp", emp);
        model.addAttribute("board", board);
        model.addAttribute("authInfo", authInfo);
        return "board/boardupdate";
    }

    @PostMapping("/board/boardupdate/{boardNum}") //게시판 업데이트
    public String noticeUpdate(@ModelAttribute("board") Board board, Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Emp emp = boardService.selectEmp(authInfo.getEmp_id());
        boardService.update(board);

        model.addAttribute("authInfo", authInfo);
        model.addAttribute("emp", emp);

        switch (board.getBoardDivision()) {
            case "회사뉴스및공지":
                return "redirect:/board/noticelist";
            case "자유게시판":
                return "redirect:/board/forumlist";
            case "사내시스템/F&Q":
                return "redirect:/board/qalist";
            default:
                return "redirect:/board/boardmain";
        }
    }

    @GetMapping("/board/boarddelete/{boardNum}") //게시판 삭제
    public String noticeDelete(@PathVariable Long boardNum, Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Emp emp = boardService.selectEmp(authInfo.getEmp_id());
        String boardDivision = boardService.selectByNum(boardNum).getBoardDivision();
        int parentNum = boardService.selectByNum(boardNum).getParentNum();
        boardService.delete(boardNum, parentNum);

        model.addAttribute("authInfo", authInfo);
        model.addAttribute("emp", emp);

        switch (boardDivision) {
            case "회사뉴스및공지":
                return "redirect:/board/noticelist";
            case "자유게시판":
                return "redirect:/board/forumlist";
            case "사내시스템/F&Q":
                return "redirect:/board/qalist";
            default:
                return "redirect:/board/boardmain";
        }
    }

    @GetMapping("/board/noticelist") //회사뉴스 및 공지
    public String notice(Model model, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardService.selectEmp(empId);
        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, "회사뉴스및공지");

        model.addAttribute("emp", emp);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("authInfo", authInfo);
        return "board/noticelist";
    }

    @GetMapping("/board/forumlist") //자유게시판
    public String forum(Model model, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }
        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, "자유게시판");
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Emp emp = boardService.selectEmp(authInfo.getEmp_id());

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("authInfo", authInfo);
        model.addAttribute("emp", emp);
        return "board/forumlist";
    }

    @GetMapping("/board/qalist") //사내시스템/F&Q
    public String qa(Model model, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }
        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, "사내시스템/F&Q");
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Emp emp = boardService.selectEmp(authInfo.getEmp_id());

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("authInfo", authInfo);
        model.addAttribute("emp", emp);
        return "board/qalist";
    }

    @GetMapping("/board/findlike") //검색 게시판
    public String findLikePage(@RequestParam("find") String find, @RequestParam("boardDivision") String boardDivision, @RequestParam("searchOption") String searchOption,
                               Model model, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, boardDivision, find, searchOption);
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Emp emp = boardService.selectEmp(authInfo.getEmp_id());

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("boardDivision", boardDivision);
        model.addAttribute("find", find);
        model.addAttribute("searchOption", searchOption);
        model.addAttribute("authInfo", authInfo);
        model.addAttribute("emp", emp);
        return "board/findlike";
    }

    @PostMapping("/board/findlike") //검색 게시판
    public String findLike(@RequestParam("find") String find, @RequestParam("boardDivision") String boardDivision, @RequestParam("searchOption") String searchOption,
                           Model model, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, boardDivision, find, searchOption);
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Emp emp = boardService.selectEmp(authInfo.getEmp_id());

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("boardDivision", boardDivision);
        model.addAttribute("find", find);
        model.addAttribute("searchOption", searchOption);
        model.addAttribute("authInfo", authInfo);
        model.addAttribute("emp", emp);
        return "board/findlike";
    }

    @GetMapping("/board/replywrite/{boardNum}") //Q&A답변 폼
    public String reply(@PathVariable Long boardNum, Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardService.selectEmp(empId);
        Board board = boardService.selectByNum(boardNum);

        model.addAttribute("emp", emp);
        model.addAttribute("board", board);
        model.addAttribute("authInfo", authInfo);
        return "board/replywrite";
    }

    @PostMapping("/board/replywrite/{boardNum}") //Q&A답변 저장
    public String replyWrite(@PathVariable Long boardNum, @ModelAttribute("board") Board board, Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardService.selectEmp(empId);
        Board pBoard = boardService.selectByNum(boardNum);
        boardService.replyWrite(pBoard, board, empId);

        model.addAttribute("authInfo", authInfo);
        model.addAttribute("emp", emp);
        return "redirect:/board/qalist";
    }
}
