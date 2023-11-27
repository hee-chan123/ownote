package com.project.ownote.annual.dao;

import com.project.ownote.annual.dto.AnnualDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AnnualDao {

    List<AnnualDto> annualAllList();
    public void writeAnnual(AnnualDto annualDto);
//    public int deleteDao(int reNum);

public int countAnnual();

     public int codeId(String anninfo_code);

    String findEmpNameById(int emp_id);
    List<AnnualDto> annualAllList(int startRow, int size);

//    List<AnnualDto>annualListByID(int emp_id);

 void increaseAnnualBySeniority();

   List<AnnualDto>  annualList22(int emp_id);

Integer countAnnualdate(int emp_id);

    AnnualDto annualModify(Long annualId);

    Integer updateCheckid(Long annual_id);


   public void minusDate (int empId);

    int rejectDate(Long annualId);
    List<AnnualDto> searchAnnualId(int emp_id);

    int countDataAnnual(int emp_id);

    Date findDate(int emp_id);

    List<AnnualDto> annualListByID(int emp_id, int startRow, int size);

    int addAnnual(int emp_id);

String findName(int emp_id);

    List<AnnualDto> find(int emp_id);















}
