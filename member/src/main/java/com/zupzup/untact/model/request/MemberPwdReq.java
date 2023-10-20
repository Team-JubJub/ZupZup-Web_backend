package com.zupzup.untact.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberPwdReq {

    private String loginId;
    private String phoneNum;
    private String loginPwd1;
    private String loginPwd2;
}
