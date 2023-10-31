package com.project.ownote.emp.login.controller;

import com.project.ownote.emp.EmpMapper;
import com.project.ownote.emp.login.dto.AuthInfo;
import com.project.ownote.emp.login.dto.Emp;
import com.project.ownote.emp.login.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/emp/logIn", method = RequestMethod.GET)
    public String getLogin() {
        return "/emp/loginForm";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public String PostLogin(@RequestParam("emp_email") String emp_email, @RequestParam("emp_password") String emp_password, HttpSession session) {
        String email = emp_email;
        AuthInfo authInfo = authService.authenticate(email, emp_password);
        System.out.println("------------------------------" + authInfo);
        session.setAttribute("authInfo", authInfo);


        Emp emp = empMapper.selectByEmail(email);
        return "success";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout (HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        session.invalidate();
        return "redirect:/";
    }
}
