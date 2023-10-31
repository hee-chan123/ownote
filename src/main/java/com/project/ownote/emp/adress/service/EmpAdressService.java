package com.project.ownote.emp.adress.service;

import com.github.pagehelper.PageHelper;
import com.project.ownote.emp.adress.dto.DeptAdressDto;
import com.project.ownote.emp.adress.dto.EmpAdressDto;
import com.project.ownote.emp.EmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageInfo;
import java.util.List;

@Service
public class EmpAdressService {

    @Autowired
    private EmpMapper empMapper;

    public Integer countEmpAdress() {
        return empMapper.countEmpAdress();
    }
    public Integer countDeptAdress(int dept_num) {return empMapper.countDeptAdress(dept_num);}
    public List<EmpAdressDto> adressListPage(int startRow, int size) {
        return empMapper.adressListPage(startRow, size);
    }
    public List<DeptAdressDto> deptAdressListPage(int startRow, int size, int dept_num) {
        return empMapper.deptAdressListPage(startRow, size, dept_num);
    }
}
