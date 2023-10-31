package com.project.ownote.emp.adress.controller;

import com.project.ownote.emp.adress.dto.DeptAdressDto;
import com.project.ownote.emp.adress.dto.DeptAdressPage;
import com.project.ownote.emp.adress.dto.EmpAdressDto;
import com.project.ownote.emp.adress.dto.EmpAdressPage;
import com.project.ownote.emp.adress.service.EmpAdressService;
import com.project.ownote.emp.login.dto.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmpAdressController {

    @Autowired
    private EmpAdressService empAdressService;
    private int size = 10;

    public EmpAdressPage getEmpAdressPage(int pageNum, int pageSize) {
        int total = empAdressService.countEmpAdress();// 전체 사용자 수 조회
        List<EmpAdressDto> empAdressDto = empAdressService.adressListPage((pageNum - 1) * size,size);
        return new EmpAdressPage(total, pageNum, size, empAdressDto);
    }

    public DeptAdressPage getDeptAdressPage(int pageNum, int pageSize, int dept_num) {
        int total = empAdressService.countDeptAdress(dept_num);// 전체 사용자 수 조회
        List<DeptAdressDto> dmpAdressDto = empAdressService.deptAdressListPage((pageNum - 1) * size, size, dept_num);
        return new DeptAdressPage(total, pageNum, size, dmpAdressDto);
    }

    @RequestMapping(value = "/emp/adress", method = RequestMethod.GET)
    public String getEmpAddress(Model model, @RequestParam(name = "pageNo", required = false) String pageNo) {
        int pageSize = 4;
        int pageNum = 1;
        try{
            if (pageNo != null) {
                pageNum = Integer.parseInt(pageNo);
            }
        } catch (NumberFormatException e) {
            pageNum = 1;
        }
        EmpAdressPage empAdressPage = getEmpAdressPage(pageNum, pageSize);
        model.addAttribute("listPage", empAdressPage);
        return "emp/adress";
    }

    @GetMapping  ("/emp/adressDept/{dept_num}")
    public String deptAdress(Model model, @RequestParam(name = "pageNo", required = false) String pageNo, @PathVariable(name = "dept_num") int dept_num) {
        int pageSize = 4;
        int pageNum = 1;
        try {
            if (pageNo != null) {
                pageNum = Integer.parseInt(pageNo);
            }
        } catch (NumberFormatException e) {
            pageNum = 1;
        }
        DeptAdressPage empAdressPage = getDeptAdressPage(pageNum, pageSize, dept_num);
        model.addAttribute("deptPage", empAdressPage);
        model.addAttribute("dept_num", dept_num);
        System.out.println("pageNo= " + pageNo);
        return "emp/adressDept";
    }
}
