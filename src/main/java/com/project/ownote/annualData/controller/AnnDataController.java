package com.project.ownote.annualData.controller;

import com.project.ownote.annual.dao.AnnualDao;
import com.project.ownote.annual.dto.AnnualDto;
import com.project.ownote.annual.dto.AnnualPage;
import com.project.ownote.annual.entity.Annual;
import com.project.ownote.annual.repository.AnnualEm;
import com.project.ownote.annualData.dto.AnnData;
import com.project.ownote.annualData.dto.AnnualDataPage;
import com.project.ownote.annualData.repository.AnnDT;
import com.project.ownote.annualData.repository.AnnualDataRepository;
import com.project.ownote.emp.login.dto.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
//@RequestMapping("/annual")

@Controller
public class AnnDataController {
    @Autowired
    AnnDT annDT;
@Autowired
    AnnualDao annualDao;
    @Autowired
    AnnualDataRepository annualDataRepository;
@Autowired
    AnnualEm annualEm;

    private int size = 10;



   public AnnualDataPage getAnnualDataPage(int pageNum, int pageSize, int empId) {

       int total = annualDao.countDataAnnual(empId);
       List<AnnualDto> content = annualDao.annualListByID((pageNum -1) * size, size,empId);
       return new AnnualDataPage(total, pageNum,size,content);}


    @RequestMapping(value = "/annual/anndata", method = RequestMethod.GET)
    public String AnnualData(Model model, HttpSession session, @RequestParam(name = "pageNo", required = false) String pageNo){

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        int empId= authInfo.getEmp_id();
        int pageSize = 4; // 페이지 크기 설정 (한 페이지에 보여줄 회원 수)
        int pageNum = 1;

        if (pageNo != null) {
            pageNum = Integer.parseInt(pageNo);
        }

         addAnnual(empId);

        AnnualDataPage annualPage = getAnnualDataPage(pageNum, pageSize,empId);

        model.addAttribute("annualPage", annualPage);
        model.addAttribute("pageNo", pageNo);


        List<AnnData> list = annDT.selectListAll(empId);
        model.addAttribute("list",list);

        List<AnnualDto>list1= annualDao.annualListByID(empId,(pageNum -1) * size, size);
        model.addAttribute("list1",list1);
        return "annual/AnnualData";
    }
    private int add = 1;

    public void addAnnual (int emp_id) {

    Date hireDate = annualDao.findDate(emp_id);
    Date currentDate = new Date(); // 현재 날짜
        System.out.println("=======================================입사날짜"+hireDate);

    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthDayFormat = new SimpleDateFormat("MM-dd");

    String formattedHireYear = yearFormat.format(hireDate);

    int hiredate = Integer.parseInt(formattedHireYear);
        System.out.println("입사년도===================================="+hiredate);
    String formattedCurrentYear = yearFormat.format(currentDate);

        int currentdate = Integer.parseInt(formattedCurrentYear);
        System.out.println("현재년도===================================="+currentdate);
    if ((hiredate - currentdate) % 2 == 0) {
        // 연도를 비교해서 홀수일 때만 아래 코드 실행
        System.out.println("연수 뺀 값=================================================================="+(Integer.parseInt(formattedCurrentYear) - Integer.parseInt(formattedHireYear)) % 2);
        String formattedHireDate = monthDayFormat.format(hireDate);
        String formattedCurrentDate = monthDayFormat.format(currentDate);

        if (formattedHireDate.equals(formattedCurrentDate)) {
            if (add == 1) {
                add += 1;
                annualDao.addAnnual(emp_id);


                System.out.println("add의 값"+add);
            }else if(add == 2){

            }
        }
    } else if ((hiredate - currentdate) % 2 == 1) {
        add -= 1;
    }


}
}
