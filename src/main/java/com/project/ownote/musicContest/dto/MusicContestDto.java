package com.project.ownote.musicContest.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MusicContestDto {
    private int musiccontest_id;
    private int musiccontest_num;
    private String musiccontest_emp_name;
    private String musiccontest_dept_name;
    private String musiccontest_title;
    private String musiccontest_content;
    private Date musiccontest_regdate;
    private int musiccontest_likes;
}
