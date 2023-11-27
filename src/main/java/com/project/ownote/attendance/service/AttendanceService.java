package com.project.ownote.attendance.service;

import com.project.ownote.attendance.dao.AttendanceDao;
import com.project.ownote.attendance.dto.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class AttendanceService {
    private final AttendanceDao attendanceDao;


    @Autowired
    public AttendanceService(AttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
    }


    public void saveAttendance(Attendance attendance) {
        attendanceDao.saveAttendance(attendance);
    }


    public Attendance getAttendanceById(Long attendance_id) {
        return attendanceDao.getAttendanceById(attendance_id);
    }


    public List<Attendance> getAllAttendances() {
        return attendanceDao.getAllAttendances();
    }




//    public void deleteAttendance(Long attendanceId) {
//        attendanceDao.deleteAttendance(attendanceId);
//    }

    public void updateAttendance(Attendance attendance) {
        attendanceDao.updateAttendance(attendance);
    }

    public Attendance getAttendanceByAttendanceId(Long attendanceId) {
        return attendanceDao.getAttendanceByAttendanceId(attendanceId);
    }





    public void deleteById(Long attendanceId) {
             attendanceDao.deleteById(attendanceId);
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


    public void recordAttendance(String attendanceTime) {
    }

    public void recordLeave(String leaveTime) {
    }



    public List<Attendance> findByEmpNum(Long emp_num){
        return attendanceDao.findByEmpNum(emp_num);
    }

    public  List<Attendance> findByEmpNumAAndAtt_date(Long emp_num, LocalDate att_date) {
        return attendanceDao.findByEmpNumAAndAtt_date(emp_num, att_date);
    }

    public  List<Attendance> findByEmpNumAndAtt_dateAndatAndAtt_offtime(Long emp_num, LocalDate att_date, LocalTime att_offtime) {
        return attendanceDao.findByEmpNumAndAtt_dateAndatAndAtt_offtime(emp_num, att_date,att_offtime);
    }

}

