package com.project.ownote.attendance.controller;

import com.project.ownote.attendance.dto.Attendance;
import com.project.ownote.attendance.dto.Dto;
import com.project.ownote.attendance.repository.AttendanceRepository;
import com.project.ownote.attendance.service.AttendanceService;
import com.project.ownote.emp.login.dto.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }






    @GetMapping("/list1")
    public String listAllAttendances(Model model,HttpSession session) {
        List<Attendance> allAttendances = attendanceService.getAllAttendances();
        if(allAttendances == null || allAttendances.isEmpty()) {
            return "attendance/attendancelist";
        }
        model.addAttribute("allAttendances", allAttendances);

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        return "attendance/list";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long attendance_id,HttpSession session) {
        Attendance attendance =  attendanceService.getAttendanceById(attendance_id);
        System.out.println("컨트롤러");
        System.out.println(attendance);
//        List<Attendance> allAttendances = attendanceService.getAllAttendances();
       model.addAttribute("allAttendances", attendanceService.getAttendanceById(attendance_id));


        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        return "attendance/edit";
    }




    @PostMapping("/update")
    public String update1(@ModelAttribute Dto dto,HttpSession session,Model model) {
        // 입력 문자열
        String timeString = dto.getAtt_offtime();
        String timeString2 = dto.getAtt_ontime();
        String status = dto.getAtt_status();
        System.out.println("3038403840385038503583" + timeString2);

        // 파싱할 시간 형식 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        // 문자열을 LocalTime으로 변환
        LocalTime localTime = LocalTime.parse(timeString, formatter);
        LocalTime localTime2 = LocalTime.parse(timeString2, formatter);


        Attendance attendance = attendanceService.getAttendanceById(dto.getAttendance_id());
        attendance.setAtt_offtime(localTime);
        attendance.setAtt_ontime(localTime2);
        attendance.setAtt_status(status);
        System.out.println("----------------------" + attendance);

//        Attendance attendance = attendanceService.getAttendanceById(attendance);
//        if (attendance != null) {

        attendanceService.updateAttendance(attendance);

//        }

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        return "redirect:/attendance/list1";
    }





    @GetMapping("/record")
    public String recordAttendancePage(Model model,HttpSession session) {
        model.addAttribute("attendance", new Attendance());
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        return "attendance/attendance";
    }

    @GetMapping("/record/go")
    public String recordAttendancePage(HttpSession session,Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        System.out.println("0000000000000000000000000000000000000000000000"+attendanceService.findByEmpNumAAndAtt_date((long)authInfo.getEmp_num(), currentDate));
        if(!attendanceService.findByEmpNumAAndAtt_date((long)authInfo.getEmp_num(), currentDate).isEmpty()) {
            System.out.println("##############################################################됨");
            String alertMessage = "이미 출근 기록이 존재합니다";
            model.addAttribute("alertMessage", alertMessage);
            return "attendance/attendance";
        } else {
            Attendance attendance = new Attendance(0L, (long) authInfo.getEmp_num(), currentDate, currentTime, null, "출근", authInfo.getEmp_name(), authInfo.getGrade_name(), authInfo.getDept_name());
            attendanceService.saveAttendance(attendance);
            System.out.println("----------------------------" + currentDate);
            System.out.println("----------------------------" + currentTime);
            return "redirect:/";
        }
    }



    @GetMapping("/record/leave")
    public String recordAttendancePageP(HttpSession session,Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        int emp_num = authInfo.getEmp_num();

        if(attendanceService.findByEmpNumAAndAtt_date((long)authInfo.getEmp_num(),currentDate).get(0).getAtt_offtime() != null
        ) {
            System.out.println("##############################################################됨");
            String alertMessage = "이미 퇴근 기록이 존재합니다";
            model.addAttribute("alertMessage2", alertMessage);
            return "attendance/attendance";
        } else {

            List<Attendance> listAttendance = attendanceService.findByEmpNumAAndAtt_date((long) emp_num, currentDate);
            Attendance attendance = listAttendance.get(0);
            System.out.println(attendance);
            attendance.setAtt_status("퇴근");
            attendance.setAtt_offtime(currentTime);
            attendanceService.updateAttendance(attendance);
            System.out.println("----------------------------" + currentDate);
            System.out.println("----------------------------" + currentTime);

            return "redirect:/";
        }
    }





    @GetMapping("/search")
    public  String search(Model model, @RequestParam(required = false, defaultValue = "0") Long emp_num,HttpSession session){
       if(attendanceService.findByEmpNum(emp_num)==null || attendanceService.findByEmpNum(emp_num).isEmpty() || emp_num == 0L){
           System.out.println("__________________________________" +emp_num);
           String nullAlert = "존재하지 않습니다.";
           model.addAttribute("nullAlert",nullAlert);
           return "attendance/list";
       } else {
           List<Attendance> list = attendanceService.findByEmpNum(emp_num);
           model.addAttribute("allAttendances", list);
           AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
           model.addAttribute("authInfo", authInfo);
           return "attendance/list";
       }
    }


    @GetMapping("/delete/{attendance_id}")
    public String delete(@PathVariable Long attendance_id,HttpSession session,Model model) {

        attendanceService.deleteById(attendance_id);
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        return "redirect:/attendance/list1";
    }



}
