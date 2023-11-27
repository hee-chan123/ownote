package com.project.ownote.emp.mypage.controller;

import com.project.ownote.emp.login.dto.AuthInfo;
import com.project.ownote.emp.login.dto.Emp;
import com.project.ownote.emp.mypage.dto.MyPage;
import com.project.ownote.emp.mypage.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MyPageController {
    @Autowired
    MyPageService myPageService;

    @RequestMapping(value = "/emp/myPage", method = RequestMethod.GET)
    public String myPage(Model model, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        String emp_email = authInfo.getEmp_email();
        Emp myPage = myPageService.selectByEmailForMyPage(emp_email);
        model.addAttribute("myPage", myPage);
        model.addAttribute("authInfo", authInfo);
        return "emp/myPage";
    }
}
