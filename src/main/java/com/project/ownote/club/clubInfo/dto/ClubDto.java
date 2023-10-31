package com.project.ownote.club.clubInfo.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClubDto {  //Club DB all data

    private Long club_id;
    private String club_emp_name;    // emp_name ( emp table import_)
    private String club_name;
    private String clubcategory_name;
    private String club_sub;
    private Date club_regdate;
    private String clubcategory_file;


}
