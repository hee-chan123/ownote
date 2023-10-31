package com.project.ownote.emp.mypage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePhoneCommand {
    private String old_emp_phone;
    private String new_emp_phone;
    private String old_emp_password;
    private String new_emp_password;
}
