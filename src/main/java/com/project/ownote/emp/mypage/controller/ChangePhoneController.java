package com.project.ownote.emp.mypage.controller;

import com.project.ownote.emp.login.dto.AuthInfo;
import com.project.ownote.emp.login.dto.Emp;
import com.project.ownote.emp.login.service.AuthService;
import com.project.ownote.emp.mypage.dto.ChangePhoneCommand;
import com.project.ownote.emp.mypage.dto.MyPage;
import com.project.ownote.emp.mypage.service.ChangePhoneService;
import com.project.ownote.emp.mypage.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class ChangePhoneController {
    @Autowired
    private ChangePhoneService changePhoneService;
    @RequestMapping(value = "/emp/changePhone", method = RequestMethod.GET)
    public String getChangePhone (Model model, HttpSession session) {
        AuthInfo authInfo= (AuthInfo) session.getAttribute("authInfo");
        Emp emp = changePhoneService.selectByEmail2(authInfo.getEmp_email());
        System.out.println("-----------------------------------"+emp);
        model.addAttribute("emp", emp);
        return "emp/changePhoneForm";
    }

    @RequestMapping(value = "/emp/changePhone", method = RequestMethod.POST)
    @ResponseBody
    public String postChangePhone(@ModelAttribute("command") ChangePhoneCommand changePhoneCommand, HttpSession session){
        //현재 사용자의 인증 정보를 세션에서 가져옴
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        System.out.println(changePhoneCommand);
            changePhoneService.changeEmpPhone(
                    authInfo.getEmp_email(),
                    changePhoneCommand.getOld_emp_phone(),
                    changePhoneCommand.getNew_emp_phone(),
                    changePhoneCommand.getOld_emp_password(),
                    changePhoneCommand.getNew_emp_password()
            );
            return "/logout";
    }
}
