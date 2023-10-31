package com.project.ownote.annual.controller;

import com.project.ownote.annual.dao.AnnualDao;
import com.project.ownote.annual.entity.Annual;
import com.project.ownote.annual.dto.AnnualDto;
import com.project.ownote.annual.dto.AnnualPage;
import com.project.ownote.annual.repository.AnnualEm;
import com.project.ownote.annual.repository.AnnualRepository;
import com.project.ownote.annual.service.AnnualService;
import com.project.ownote.annualData.repository.AnnDT;
import com.project.ownote.emp.login.dto.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class AnnualController {

    @Autowired
    AnnualDao annualDao;
    @Autowired
    AnnualService annualService;
    @Autowired
    AnnualRepository annualRepository;
    @Autowired
    AnnualEm annualEm;
    @Autowired
    AnnDT annDT;
    private int size = 10;
    public AnnualPage getAnnualPage (int pageNum, int pageSize){
        int total = annualDao.countAnnual();
        List<AnnualDto> content = annualDao.annualAllList((pageNum -1) * size, size);
        return new AnnualPage(total, pageNum,size,content);
    }



//    @GetMapping("/write")
//    public String write() {
//
//        return "annual/annualWrite";
//    }

    @GetMapping("/annual/write")
    public String write(HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId= authInfo.getEmp_id();

        return "annual/annualWrite";
    }

    @PostMapping("/annual/write")
    public String write2(Model model, AnnualDto annualDto, @RequestParam String anninfo_code,HttpSession session) {
        ;
//        LocalTime midnight = LocalTime.MIDNIGHT;
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        int empId= authInfo.getEmp_id();
        annualDto.setEmp_id(empId);
        Double countdate =annDT.selectData(empId);
        System.out.println("======================================countdate"+countdate);
        if(countdate!=0.0&&countdate!=0 &&countdate>0.0){
            annualDao.writeAnnual(annualDto);
            return "annual/writeSuccess";
        }else {

            return "redirect:/annual/write?error=1";
        }

    }


    @RequestMapping(value = "/annual/list", method = RequestMethod.GET)
    public String getAnnualList(Model model, @RequestParam(name = "pageNo", required = false) String pageNo, HttpSession session) {

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo",authInfo);
        Integer grade_num= authInfo.getGrade_num();
        int empId= authInfo.getEmp_id();
        System.out.println("===================================================================="+authInfo.getGrade_num());
        int pageSize = 4; // 페이지 크기 설정 (한 페이지에 보여줄 회원 수)
        int pageNum = 1;

//        List<AnnualDto> search= annualDao.searchAnnualId(1);
//        for(AnnualDto list : search){
//            System.out.println( list);
//        }
//        System.out.println("====================검색 "+search);

        if (pageNo != null) {
            pageNum = Integer.parseInt(pageNo);
        }
        AnnualPage annualPage = getAnnualPage(pageNum, pageSize);
        log.info("-------" + annualPage);
        model.addAttribute("annualPage", annualPage);
        model.addAttribute("pageNo", pageNo);
        return "annual/list";
    }



   // @RequestMapping(value = "/annual/search", method = RequestMethod.POST)
//public String searchAnnualId(Model model,@RequestParam("search-option")String search){
    //List<AnnualDto> searchResults =  annualDao.searchAnnualId();
   // model.addAttribute("searchAnnualId",searchResults);


//return  "searchResults";

//}






    @RequestMapping(value = "/delete/{annual_id}", method = RequestMethod.GET)
    public String annDelete(@PathVariable Long annual_id, Model model,HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId= authInfo.getEmp_id();
        annualEm.annDeleteById(annual_id);
        return "redirect:/annual/anndata";
    }







    @RequestMapping(value = "/annual/annModify/{annual_id}", method = RequestMethod.GET)
    public String annModify(@PathVariable Long annual_id, Model model,HttpSession session) {

        Annual annual = annualEm.annFindById(annual_id);
        model.addAttribute("annual", annual);
        return "annual/annualModify";
    }
    @RequestMapping(value = "/annual/annualModify", method = RequestMethod.POST)
    @Transactional
    public String update(@ModelAttribute Annual annual,
                         Model model,HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId= authInfo.getEmp_id();
        annual.setEmp_id(empId);
        annualEm.annModify(annual);
        return "redirect:/annual/anndata";
    }





    @RequestMapping(value = "/annual/anndataModify/{annual_id}", method = RequestMethod.GET)
    public String anndataModify(@PathVariable Long annual_id, Model model) {

        Annual annual = annualEm.annFindById(annual_id);
        model.addAttribute("annual", annual);
        model.addAttribute("annual_id", annual.getAnnual_id());
        return "annual/dataModify";
    }


    @RequestMapping(value = "/annual/datalist", method = RequestMethod.POST)
    @Transactional
    public String anndataModify2(@RequestParam("annual_id") Long annual_id,
                                 Model model, HttpSession session, @RequestParam("anncheck_id") int anncheck_id) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int empId= authInfo.getEmp_id();
        Annual annual = annualEm.annFindById(annual_id);
        annual.setEmp_id(empId);

        Long annualId =annual.getAnnual_id();
        model.addAttribute("annual",annual);
        model.addAttribute("anncheck_id",anncheck_id);
//        model.addAttribute("annualDto",annualDto);
        System.out.println("=======================anncheck_id"+anncheck_id);
        System.out.println("=======================annual.getAnnual_id"+annual.getAnnual_id());


        if(anncheck_id==2){
            annualDao.updateCheckid(annual.getAnnual_id());
            annualDao.minusDate(empId);


        }else if (anncheck_id==3){
            annualDao.rejectDate(annualId);
        }

        return "redirect:/annual/list";
    }





}








