package com.project.ownote.emp.login.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthInfo {
    private int emp_id;
    private int emp_num;
    private String dept_name;
    private String grade_name;
    private String emp_password;
    private String emp_name;
    private String emp_email;
    private int grade_num;
    private int dept_num;
}
