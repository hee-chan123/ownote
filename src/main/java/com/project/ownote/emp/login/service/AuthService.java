package com.project.ownote.emp.login.service;

import com.project.ownote.emp.EmpMapper;
import com.project.ownote.emp.login.dto.AuthInfo;
import com.project.ownote.emp.login.dto.Emp;
import com.project.ownote.emp.login.dto.LoginDto;
import com.project.ownote.emp.login.exception.WrongIdPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmpMapper empMapper;

    public AuthInfo authenticate(String email, String password) {
        Emp emp = empMapper.selectByEmail(email);
        String dept_name = empMapper.deptByNum(emp.getDept_num());
        String grade_name = empMapper.gradeByNum(emp.getGrade_num());
        if (emp == null) {
            return new AuthInfo(emp.getEmp_id(),emp.getEmp_num(),dept_name,grade_name,emp.getEmp_password(),"error999",emp.getEmp_email(), emp.getGrade_num(), emp.getDept_num());
        }
        if (!emp.matchPassword(password)) {
            return new AuthInfo(emp.getEmp_id(),emp.getEmp_num(),dept_name,grade_name,emp.getEmp_password(),"error999",emp.getEmp_email(), emp.getGrade_num(), emp.getDept_num());
        }
        return new AuthInfo(emp.getEmp_id(),emp.getEmp_num(),dept_name,grade_name,emp.getEmp_password(),emp.getEmp_name(),emp.getEmp_email(), emp.getGrade_num(), emp.getDept_num());
    }
}
