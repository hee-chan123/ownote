package com.project.ownote.attendance.dto;

import lombok.*;

import java.security.PrivateKey;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dto {
    private Long attendance_id;
    private String att_ontime;
    private String att_offtime;
    private String att_status;
}
