package com.project.ownote.board.Controller;

import com.project.ownote.board.dto.Board;
import com.project.ownote.board.dao.BoardDao;
import com.project.ownote.board.page.BoardPage;
import com.project.ownote.board.page.ListBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    BoardDao boardDao;

    @Autowired
    ListBoard listBoard;

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
    public String noticeWrite(@ModelAttribute("board") Board board){
        boardDao.write(board);
        boardDao.parentNumUpdate(boardDao.maxBoardNum());
        return "redirect:/board/boardmain";
    }

    @GetMapping("/board/boardupdate/{boardNum}") //게시판 업데이트 폼
    public String noticeUpdateForm(@PathVariable Long boardNum, Model model){
        Board board = boardDao.selectByNum(boardNum);

        model.addAttribute("board", board);
        return "board/boardUpdate";
    }

    @PostMapping("/board/boardupdate/{boardNum}") //게시판 업데이트
    public String noticeUpdate(@ModelAttribute("board") Board board){
        boardDao.update(board);
        return "redirect:/board/boardmain";
    }

    @GetMapping("/board/boarddelete/{boardNum}") //게시판 삭제
    public String noticeDelete(@PathVariable Long boardNum){
        boardDao.delete(boardNum);
        return "redirect:/board/boardmain";
    }

    @GetMapping("/board/noticeList") //공지사항 이동
    public String notice(Model model, @RequestParam(value = "pageNo", required = false) String pageNoVal){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        List<Board> boardList = boardDao.select((pageNo - 1) * 10, 10, "공지사항");
        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, "공지사항");

        model.addAttribute("boardList", boardList);
        model.addAttribute("boardPage", boardPage);
        return "board/noticeList";
    }

    @GetMapping("/board/forumList") //자유게시판 이동
    public String forum(Model model, @RequestParam(value = "pageNo", required = false) String pageNoVal){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        List<Board> boardList = boardDao.select((pageNo - 1) * 10, 10, "자유게시판");
        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, "자유게시판");

        model.addAttribute("boardList", boardList);
        model.addAttribute("boardPage", boardPage);
        return "board/forumList";
    }

    @GetMapping("/board/qaList") //Q&A 이동
    public String qa(Model model, @RequestParam(value = "pageNo", required = false) String pageNoVal){
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        List<Board> boardList = boardDao.select((pageNo - 1) * 10, 10, "Q&A");
        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, "Q&A");

        model.addAttribute("boardList", boardList);
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

        List<Board> boardList = boardDao.select((pageNo - 1) * 10, 10, boardDivision, find);
        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, boardDivision, find);

        model.addAttribute("boardList", boardList);
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

        List<Board> boardList = boardDao.select((pageNo - 1) * 10, 10, boardDivision, find);
        BoardPage boardPage = listBoard.getBoardPage((long) pageNo, boardDivision, find);

        model.addAttribute("boardList", boardList);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("boardDivision", boardDivision);
        model.addAttribute("find", find);
        return "board/findLike";
    }

    @GetMapping("/board/replywrite/{boardNum}") //Q&A답변 폼
    public String reply(@PathVariable Long boardNum, Model model){
        Board board = boardDao.selectByNum(boardNum);

        model.addAttribute("board", board);
        return "board/replyWrite";
    }

    @PostMapping("/board/replywrite/{boardNum}") //Q&A답변 저장
    public String replywrite(@PathVariable Long boardNum, @ModelAttribute("board") Board board){
        Board pBoard = boardDao.selectByNum(boardNum);
        boardDao.replywrite(pBoard, board);
        return "redirect:/board/qaList";
    }
}
