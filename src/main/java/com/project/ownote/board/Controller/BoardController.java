package com.project.ownote.board.Controller;

import com.project.ownote.board.dao.BoardDao;
import com.project.ownote.board.dto.Board;
import com.project.ownote.board.page.BoardPage;
import com.project.ownote.board.page.ListBoard;
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
    BoardDao boardDao;

    @Autowired
    ListBoard listBoard;

    @GetMapping("/board/boardmain") //게시판 메인
    public String boardMain(Model model){
        List<Board> boardNotice = boardDao.select(0, 5, "공지사항");
        List<Board> boardForum = boardDao.select(0, 5, "자유게시판");
        List<Board> boardQa = boardDao.select(0, 5, "Q&A");

        model.addAttribute("boardNotice", boardNotice);
        model.addAttribute("boardForum", boardForum);
        model.addAttribute("boardQa", boardQa);

        return "board/boardMain";
    }

    @GetMapping("/board/boardView/{boardNum}") //게시판 뷰
    public String View(@PathVariable Long boardNum, Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardDao.selectEmp(empId);
        Board board = boardDao.selectByNum(boardNum);
        boardDao.hitPlus(boardNum);

        model.addAttribute("emp", emp);
        model.addAttribute("board", board);
        return "board/boardView";
    }

    @GetMapping("/board/boardwriteform") //게시판 글 쓰기 폼
    public String noticeWriteForm(Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardDao.selectEmp(empId);

        model.addAttribute("emp", emp);
        return "board/boardWrite";
    }

    @PostMapping("/board/boardwrite") //게시판 글 쓰기
    public String noticeWrite(@ModelAttribute("board") Board board, HttpSession session){
        if(board.getBoardTitle().trim().isEmpty() || board.getBoardContent().trim().isEmpty()){
            switch (board.getBoardDivision()) {
                case "공지사항":
                    return "redirect:/board/noticeList";
                case "자유게시판":
                    return "redirect:/board/forumList";
                case "Q&A":
                    return "redirect:/board/qaList";
                default:
                    return "redirect:/board/boardmain";
            }
        }

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();

        boardDao.write(board, empId);
        boardDao.parentNumUpdate(boardDao.maxBoardNum());

        switch (board.getBoardDivision()) {
            case "공지사항":
                return "redirect:/board/noticeList";
            case "자유게시판":
                return "redirect:/board/forumList";
            case "Q&A":
                return "redirect:/board/qaList";
            default:
                return "redirect:/board/boardmain";
        }
    }

    @GetMapping("/board/boardupdate/{boardNum}") //게시판 업데이트 폼
    public String noticeUpdateForm(@PathVariable Long boardNum, Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardDao.selectEmp(empId);
        Board board = boardDao.selectByNum(boardNum);

        model.addAttribute("emp", emp);
        model.addAttribute("board", board);
        return "board/boardUpdate";
    }

    @PostMapping("/board/boardupdate/{boardNum}") //게시판 업데이트
    public String noticeUpdate(@ModelAttribute("board") Board board){
        if(board.getBoardTitle().trim().isEmpty() || board.getBoardContent().trim().isEmpty()){
            switch (board.getBoardDivision()) {
                case "공지사항":
                    return "redirect:/board/noticeList";
                case "자유게시판":
                    return "redirect:/board/forumList";
                case "Q&A":
                    return "redirect:/board/qaList";
                default:
                    return "redirect:/board/boardmain";
            }
        }

        boardDao.update(board);

        switch (board.getBoardDivision()) {
            case "공지사항":
                return "redirect:/board/noticeList";
            case "자유게시판":
                return "redirect:/board/forumList";
            case "Q&A":
                return "redirect:/board/qaList";
            default:
                return "redirect:/board/boardmain";
        }
    }

    @GetMapping("/board/boarddelete/{boardNum}") //게시판 삭제
    public String noticeDelete(@PathVariable Long boardNum){
        String boardDivision = boardDao.selectByNum(boardNum).getBoardDivision();
        boardDao.delete(boardNum);

        switch (boardDivision) {
            case "공지사항":
                return "redirect:/board/noticeList";
            case "자유게시판":
                return "redirect:/board/forumList";
            case "Q&A":
                return "redirect:/board/qaList";
            default:
                return "redirect:/board/boardmain";
        }
    }

    @GetMapping("/board/noticeList") //공지사항
    public String notice(Model model, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardDao.selectEmp(empId);
        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, "공지사항");

        model.addAttribute("emp", emp);
        model.addAttribute("boardPage", boardPage);
        return "board/noticeList";
    }

    @GetMapping("/board/forumList") //자유게시판
    public String forum(Model model, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardDao.selectEmp(empId);
        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, "자유게시판");

        model.addAttribute("emp", emp);
        model.addAttribute("boardPage", boardPage);
        return "board/forumList";
    }

    @GetMapping("/board/qaList") //Q&A
    public String qa(Model model, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }


        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardDao.selectEmp(empId);
        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, "Q&A");

        model.addAttribute("emp", emp);
        model.addAttribute("boardPage", boardPage);
        return "board/qaList";
    }

    @GetMapping("/board/findLike") //검색 게시판
    public String findLikePage(@RequestParam("find") String find, @RequestParam("boardDivision") String boardDivision, Model model,
                           @RequestParam(value = "pageNo", required = false) String pageNoVal){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, boardDivision, find);

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("boardDivision", boardDivision);
        model.addAttribute("find", find);
        return "board/findLike";
    }

    @PostMapping("/board/findLike") //검색 게시판
    public String findLike(@RequestParam("find") String find, @RequestParam("boardDivision") String boardDivision, Model model,
                           @RequestParam(value = "pageNo", required = false) String pageNoVal){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, boardDivision, find);

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("boardDivision", boardDivision);
        model.addAttribute("find", find);
        return "board/findLike";
    }

    @GetMapping("/board/replywrite/{boardNum}") //Q&A답변 폼
    public String reply(@PathVariable Long boardNum, Model model, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Emp emp = boardDao.selectEmp(empId);
        Board board = boardDao.selectByNum(boardNum);

        model.addAttribute("emp", emp);
        model.addAttribute("board", board);
        return "board/replyWrite";
    }

    @PostMapping("/board/replywrite/{boardNum}") //Q&A답변 저장
    public String replywrite(@PathVariable Long boardNum, @ModelAttribute("board") Board board, HttpSession session){
        if(board.getBoardTitle().trim().isEmpty() || board.getBoardContent().trim().isEmpty()){
            switch (board.getBoardDivision()) {
                case "공지사항":
                    return "redirect:/board/noticeList";
                case "자유게시판":
                    return "redirect:/board/forumList";
                case "Q&A":
                    return "redirect:/board/qaList";
                default:
                    return "redirect:/board/boardmain";
            }
        }

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();
        Board pBoard = boardDao.selectByNum(boardNum);
        boardDao.replywrite(pBoard, board, empId);
        return "redirect:/board/qaList";
    }
}
