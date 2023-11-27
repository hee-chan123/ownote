package com.project.ownote.attendance.dao;

import com.project.ownote.attendance.dto.Attendance;
import com.project.ownote.attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AttendanceDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AttendanceRepository attendanceRepository;




    public void saveAttendance(Attendance attendance) {
        attendanceRepository.save(attendance);
    }


    //    public Attendance getAttendanceById(Long attendance_id) {
//        System.out.println("엥" + attendanceRepository.findById(attendance_id).orElse(null));
//        System.out.println("ㅇ2");
//        return attendanceRepository.findById(attendance_id).orElse(null);
//    }
    public Attendance getAttendanceById(Long attendance_id) {
        System.out.println("DAO");
        System.out.println(attendance_id);
        Optional<Attendance> result = attendanceRepository.findById(attendance_id);
        System.out.println(result);
        if (result.isPresent()) {
            Attendance attendance = result.get();
            System.out.println(attendance);
            return attendance;
        }
        return null;
    }


    public Attendance getAttendanceByAttendanceId(Long attendanceId) {
        return attendanceRepository.findById(attendanceId).orElse(null);
    }





    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }


    public void updateAttendance(Attendance attendance) {
        if (attendance != null && attendance.getAttendance_id() != null) {
            attendanceRepository.save(attendance);
        }
    }


    public void recordAttendance(Long attendanceId, LocalTime onTime) {
        Attendance attendance = getAttendanceById(attendanceId);
        if (attendance != null) {
            attendance.recordAttendance(onTime);
            updateAttendance(attendance);
        }
    }


    public void recordLeave(Long attendanceId, LocalTime offTime) {
        Attendance attendance = getAttendanceById(attendanceId);
        if (attendance != null) {
            attendance.recordLeave(offTime);
            updateAttendance(attendance);
        }
    }




    public void editAttendance(Attendance attendance) {
    }


    public List<Attendance> findByEmpNum(Long emp_num) {
        return  attendanceRepository.findByEmpNum(emp_num);
    }

    public List<Attendance> findByEmpNumAAndAtt_date(Long emp_num, LocalDate att_date) {
        return attendanceRepository.findByEmpNumAndAtt_date(emp_num, att_date);
    }

    public List<Attendance> findByEmpNumAndAtt_dateAndatAndAtt_offtime(Long emp_num, LocalDate att_date, LocalTime att_offtime) {
        return attendanceRepository.findByEmpNumAndAtt_dateAndatAndAtt_offtime(emp_num, att_date,att_offtime);
    }

    public void  deleteById(Long attendace_id) {
         attendanceRepository.deleteById(attendace_id);
    }

}



