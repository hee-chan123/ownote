package com.project.ownote.club.clubPR.dto;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClubBoardDto {

    private Long clubboard_id;
    private String clubboard_emp_name;
    private String clubboard_dept_name;
    private String clubboard_title;
    private String clubboard_filename;
    private String clubboard_filepath;
    private String clubboard_sub;
    private Date clubboard_regdate;


}
