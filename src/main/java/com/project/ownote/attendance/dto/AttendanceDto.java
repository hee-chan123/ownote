package com.project.ownote.attendance.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class AttendanceDto {

    @Id
    private Long attendance_id;
    private LocalDate att_date;
    private Time att_ontime;
    private Time att_offtime;
    private String att_status;
    private String emp_name;
    private String att_gradename;
    private String att_deptname;
    private Long emp_num;


    public void recordAttendance(LocalTime onTime) {
        att_ontime = Time.valueOf(onTime);
        att_status = "출근";
    }

    public void recordLeave(LocalTime offTime) {
        att_offtime = Time.valueOf(offTime);
        att_status = "퇴근";
    }
}
