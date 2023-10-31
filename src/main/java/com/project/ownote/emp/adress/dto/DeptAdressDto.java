package com.project.ownote.emp.adress.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptAdressDto {

    private String empName;
    private String empEmail; // 필드 이름 수정
    private int dept_num;
    private String deptName; // deptName 추가
    private String gradeName; // gradeName 추가

    public DeptAdressDto(String empName, String empEmail, int dept_num, String deptName, String gradeName) {
        this.empName = empName;
        this.empEmail = empEmail;
        this.dept_num = dept_num;
        this.deptName = deptName;
        this.gradeName = gradeName;
    }
}
