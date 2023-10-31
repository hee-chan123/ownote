package com.project.ownote.emp.login.dto;

import com.project.ownote.emp.login.exception.WrongIdPasswordException;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Emp {
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
    private String grade_name;
    private String dept_name;

    public void changePassword(String oldPassword, String newPassword) {
        if (!emp_password.equals(oldPassword))
            throw new WrongIdPasswordException();
        this.emp_password = newPassword;
    }

    public void changeEmpPhone(String old_emp_phone, String new_emp_phone, String old_emp_password, String new_emp_password) {
        if (!emp_phone.equals(old_emp_phone) && !emp_password.equals(old_emp_password))
            throw new WrongIdPasswordException();
        this.emp_phone = new_emp_phone;
        this.emp_password = new_emp_password;
    }
    public boolean matchPassword(String password) {
        return this.emp_password.equals(password);}

    public Emp(int emp_id, int emp_num, String emp_password, String emp_name, String emp_birth, String emp_email, String emp_phone, Date emp_date, int grade_num, int dept_num) {
        this.emp_id = emp_id;
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
