package com.zupzup.untact.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberReq {

    private String loginId;
    private String loginPwd;
    private String email;
}
