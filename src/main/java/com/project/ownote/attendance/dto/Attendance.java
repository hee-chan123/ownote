package com.project.ownote.attendance.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "attendance")
public class Attendance {
    @Id
    private Long attendance_id;
    private Long emp_num;
    private LocalDate att_date;
    private LocalTime att_ontime;
    private LocalTime att_offtime;
    private String att_status;
    private String emp_name;
    private String att_gradename;
    private String att_deptname;


    public void recordAttendance(LocalTime onTime) {
        this.att_ontime = onTime;
        this.att_status = "출근";
    }


    public void recordLeave(LocalTime offTime) {
        this.att_offtime = offTime;
        this.att_status = "퇴근";
    }


//    public void setLeaveStatus(String leaveStatus) {
//    }
}
