package com.project.ownote.emp.adress.controller;

import com.project.ownote.emp.login.dto.AuthInfo;
import com.project.ownote.emp.login.dto.Emp;
import com.project.ownote.emp.adress.service.DeleteEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class DeleteEmpController {
    @Autowired
    DeleteEmpService deleteEmpService;

    @GetMapping("/emp/view")
    public String getEmpInfo(@RequestParam(value = "emp_email") String emp_email, HttpSession session, Model model){
        Emp emp = deleteEmpService.selectByEmail(emp_email);
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        model.addAttribute("emp", emp);
        return "emp/empInfoView";
    }

    @GetMapping("/emp/delete")
    public String getDelete(@RequestParam(value = "emp_email") String emp_email) {
        deleteEmpService.deleteEmp(emp_email);
        return "redirect:/emp/adress";
    }
}
