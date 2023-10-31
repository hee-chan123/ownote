package com.project.ownote.emp.signUp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class SignUpDto {

    private int emp_id;
    private Integer emp_num;
    private String emp_password;
    private String emp_name;
    private String emp_birth;
    private String emp_email;
    private String emp_phone;
    private LocalDateTime emp_date;
    private int grade_num;
    private int dept_num;

    public SignUpDto(Integer emp_num, String emp_password, String emp_name, String emp_birth, String emp_email, String emp_phone, LocalDateTime emp_date, int grade_num, int dept_num) {
        this.emp_num = emp_num;
        this.emp_password = emp_password;
        this.emp_name = emp_name;
        this.emp_birth = emp_birth;
        this.emp_email = emp_email;
        this.emp_phone = emp_phone;
        this.emp_date = emp_date;
        this.grade_num = grade_num;
        this.dept_num = dept_num;
    }
}
