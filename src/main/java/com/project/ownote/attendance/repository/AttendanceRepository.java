package com.project.ownote.attendance.repository;

import com.project.ownote.attendance.dto.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a WHERE a.emp_num = :emp_num")
    List<Attendance> findByEmpNum(@Param("emp_num") Long emp_num);


    @Query("SELECT a FROM Attendance a WHERE a.emp_num = :emp_num and a.att_date = :att_date")
    List<Attendance> findByEmpNumAndAtt_date(@Param("emp_num") Long emp_num, @Param("att_date") LocalDate att_date);

    @Query("SELECT a FROM Attendance a WHERE a.emp_num = :emp_num and a.att_date = :att_date and a.att_offtime = :att_offtime")
    List<Attendance> findByEmpNumAndAtt_dateAndatAndAtt_offtime(@Param("emp_num") Long emp_num, @Param("att_date") LocalDate att_date, @Param("att_offtime")LocalTime att_offtime);

}

