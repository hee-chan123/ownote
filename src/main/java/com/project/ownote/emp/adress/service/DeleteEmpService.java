package com.project.ownote.emp.adress.service;

import com.project.ownote.emp.EmpMapper;
import com.project.ownote.emp.login.dto.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteEmpService {
    @Autowired
    EmpMapper empMapper;

    public Emp selectByEmail(String emp_email) {
        return empMapper.selectByEmail(emp_email);
    }

    public void deleteEmp(String emp_email){
        empMapper.deleteEmp(emp_email);
    }
}
