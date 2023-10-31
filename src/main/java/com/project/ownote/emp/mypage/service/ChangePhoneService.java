package com.project.ownote.emp.mypage.service;

import com.project.ownote.emp.EmpMapper;
import com.project.ownote.emp.login.dto.Emp;
import com.project.ownote.emp.mypage.dto.MyPage;
import com.project.ownote.emp.mypage.exception.EmpNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangePhoneService {

    @Autowired
    private EmpMapper empMapper;

    public Emp selectByEmail2(String emp_email) {
        return empMapper.selectByEmail2(emp_email);
    }


    @Transactional
    public void changeEmpPhone(String emp_email, String old_emp_phone, String new_emp_phone,
                               String old_emp_password, String new_emp_password) {
        Emp emp = empMapper.selectByEmail2(emp_email);
        if (emp == null)
            throw new EmpNotFoundException();
        emp.changeEmpPhone(old_emp_phone, new_emp_phone, old_emp_password, new_emp_password);
        empMapper.changeEmpPhone(emp);
    }
}
