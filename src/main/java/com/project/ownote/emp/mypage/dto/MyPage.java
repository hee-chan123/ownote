package com.project.ownote.emp.mypage.dto;

import com.project.ownote.emp.login.exception.WrongIdPasswordException;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MyPage {
    private int emp_id;
    private int emp_num;
    private String emp_password;
    private String emp_name;
    private String emp_birth;
    private String emp_email;
    private String emp_phone;
    private Date emp_date;
    private int grade_num;
    private int dept_num;


}
