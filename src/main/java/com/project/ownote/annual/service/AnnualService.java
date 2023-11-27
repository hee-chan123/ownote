package com.project.ownote.annual.service;

import com.project.ownote.annual.dao.AnnualDao;
import com.project.ownote.annual.dto.AnnualDto;
import com.project.ownote.annual.dto.AnnualPage;
import com.project.ownote.annual.repository.AnnualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnnualService {



@Autowired
    AnnualDao annualDao;
@Autowired
    AnnualRepository annualRepository;

public List<AnnualDto> annualPage(int startRow, int size) {return annualDao.annualAllList(startRow, size);}

public String findEmpNameById(int emp_id){
    return annualDao.findEmpNameById(emp_id);
}


public AnnualDto findById(Long annual_id){
    return annualDao.annualModify(annual_id);
}

    public List<AnnualDto>annualList22(int emp_id){
    return annualDao.annualList22(emp_id);
    }


public int countAnnualdate(int emp_id){
    return annualDao.countAnnualdate(emp_id);
}

    public List<AnnualDto> searchAnnualId(int emp_id) {
        return annualDao.searchAnnualId(emp_id);}



public void minusDate(int emp_id){
    annualDao.minusDate(emp_id);
}

    public void addAnnual(int emp_id) {
    annualDao.addAnnual(emp_id);
    }
public Date findDate(int emp_id){
    return findDate(emp_id);
}

public String findName(int emp_id){
    return findName(emp_id);
}

public List<AnnualDto> find(int emp_id){
    return annualDao.find(emp_id);
}

}
