package com.zupzup.untact.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberReq {

    private String loginId;
    private String loginPwd1;
    private String loginPwd2;
    private String email;
}
