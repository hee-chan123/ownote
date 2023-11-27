package com.project.ownote.emp;

import com.project.ownote.emp.adress.dto.DeptAdressDto;
import com.project.ownote.emp.adress.dto.EmpAdressDto;
import com.project.ownote.emp.login.dto.Emp;
import com.project.ownote.emp.login.dto.LoginDto;
import com.project.ownote.emp.mypage.dto.MyPage;
import com.project.ownote.emp.signUp.dto.SignUpDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface EmpMapper {
    public void insertEmp(SignUpDto signUpDto);
    Integer countEmpAdress();
    Integer countDeptAdress(@Param("dept_num") int dept_num);
    List<EmpAdressDto> adressListPage(@Param("startRow") int startRow, @Param("size") int size);
    List<DeptAdressDto> deptAdressListPage(@Param("startRow") int startRow, @Param("size") int size, @Param("dept_num") int dept_num);
    public List<EmpAdressDto> getEmpAddress();
    boolean checkEmail(String emp_email);

    public LoginDto loginByEmail(String emp_email);

    public Emp selectByEmailForMyPage(String emp_email);

    public Emp selectByEmail(@Param("emp_email") String emp_email);

    public Emp selectByEmail2(String emp_email);

    public void changeEmpPhone(Emp emp);

    public String deptByNum(@Param("dept_num") int dept_num);

    public String gradeByNum(@Param("grade_num") int grade_num);

    public void deleteEmp(@Param("emp_email") String emp_email);

}
