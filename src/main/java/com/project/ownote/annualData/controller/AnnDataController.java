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
    public AnnualPage getAnnualPage (int pageNum, int pageSize){
       int total = annualDao.countAnnual();
      List<AnnualDto> content = annualDao.annualAllList((pageNum -1) * size, size);
       return new AnnualPage(total, pageNum,size,content);
   }

   // private int size = 10;





//    @RequestMapping(value = "/anndata", method = RequestMethod.GET)
//    public String AnnualData(Model model){
//        List<AnnData> list = annDT.findAll();
//        model.addAttribute("list",list);
//        return "annual/AnnualData";
//    }

    @RequestMapping(value = "/annual/anndata", method = RequestMethod.GET)
    public String AnnualData(Model model, HttpSession session, @RequestParam(name = "pageNo", required = false) String pageNo){

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId= authInfo.getEmp_id();
        int pageSize = 4; // 페이지 크기 설정 (한 페이지에 보여줄 회원 수)
        int pageNum = 1;

        if (pageNo != null) {
            pageNum = Integer.parseInt(pageNo);
        }

            Integer result = addAnnual(session);

        AnnualPage annualPage = getAnnualPage(pageNum, pageSize);

        model.addAttribute("annualPage", annualPage);
        model.addAttribute("pageNo", pageNo);


        List<AnnData> list = annDT.selectListAll(empId);
        model.addAttribute("list",list);

        List<AnnualDto>list1= annualDao.annualListByID(empId,(pageNum -1) * size, size);
        model.addAttribute("list1",list1);
        return "annual/AnnualData";
    }

    public Integer addAnnual (HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId = authInfo.getEmp_id();

        Date hireDate = annualDao.findDate(empId);
        Date currentDate = new Date(); // 현재 날짜

        // SimpleDateFormat을 사용하여 월과 일 포맷 지정
        SimpleDateFormat monthDayFormat = new SimpleDateFormat("MM-dd");

        // 입사 날짜와 현재 날짜를 월과 일로 포맷팅
        String formattedHireDate = monthDayFormat.format(hireDate);
        String formattedCurrentDate = monthDayFormat.format(currentDate);

        // 월과 일로 포맷된 날짜를 비교
        if (formattedHireDate.equals(formattedCurrentDate)) {
            annualDao.addAnnual(empId);

        } else {
            return null;
        }


        return new Integer(empId);
    }


}
