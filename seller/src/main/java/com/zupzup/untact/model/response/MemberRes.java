package com.zupzup.untact.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MemberRes {

    private Long id;
    private String loginId;
    private LocalDateTime created_at;
}
