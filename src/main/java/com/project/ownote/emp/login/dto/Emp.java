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

    public Emp(int empId, int empNum, String empPassword, String empName, String empBirth, String empEmail, String empPhone, java.sql.Date empDate, int gradeNum, int deptNum) {
    }

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

    public boolean matchEmail(String email) {
        return this.emp_email.equals(email);}
}
