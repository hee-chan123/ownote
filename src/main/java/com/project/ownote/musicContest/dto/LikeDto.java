package com.project.ownote.musicContest.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LikeDto {
    private int musiccontest_like_id;
    private int musiccontest_id;
    private int emp_id;
}
