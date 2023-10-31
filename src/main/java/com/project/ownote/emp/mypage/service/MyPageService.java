package com.project.ownote.emp.mypage.service;

import com.project.ownote.emp.EmpMapper;
import com.project.ownote.emp.login.dto.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {

    @Autowired
    EmpMapper empMapper;

    public Emp selectByEmailForMyPage(String emp_email) {
        return empMapper.selectByEmailForMyPage(emp_email);
    }
}
