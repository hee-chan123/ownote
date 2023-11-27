package com.project.ownote.emp.signUp.controller;

import com.project.ownote.emp.login.dto.AuthInfo;
import com.project.ownote.emp.signUp.dto.SignUpDto;
import com.project.ownote.emp.signUp.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class SignUpController {

    @Autowired
    SignUpService signUpService;

    private int currentEmpNum = 0;

    @RequestMapping(value = "/emp/signUp", method = RequestMethod.GET)
    public String getSignUp(HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        return "emp/signUpForm";
    }

    @RequestMapping(value = "/ownote", method = RequestMethod.POST)
    public String postSignUp(@ModelAttribute SignUpDto signUpDto, RedirectAttributes redirectAttributes) {
        try {
            Integer emp_num = signUpDto.getEmp_num();
            if (emp_num == null) {
                signUpDto.setEmp_num(currentEmpNum);
            }
            if (!signUpDto.getEmp_email().contains("@")) {
                signUpDto.setEmp_email(signUpDto.getEmp_email() + "@ownote.com");
            } else {
                redirectAttributes.addFlashAttribute("error", "영문 숫자 조합만 입력이 가능합니다.");
            }
            signUpService.insertEmp(signUpDto);
            return "ownote1";
        } catch (Exception e) {
            // 회원가입 실패 처리
            redirectAttributes.addFlashAttribute("error", "회원가입에 실패했습니다. 다시 시도해주세요.");
            return "redirect:/emp/signUp";
        }
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
    @ResponseBody
    public String checkEmail(@RequestParam String emp_email) {
        boolean checkEmail = signUpService.checkEmail(emp_email);
        if (checkEmail) {
            return "이미 사용 중인 이메일입니다.";
        } else {
            return "사용 가능한 이메일입니다.";
        }
    }
}
