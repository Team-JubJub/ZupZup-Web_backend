package com.zupzup.untact.model.response;

import lombok.*;

@Getter @Setter
public class MemberLoginRes {

    private String loginId;
    private String token;
    private Long id; // 사용자 unique Id
    private int cnt;
}
