package com.zupzup.untact.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginReq {

    private String loginId;
    private String loginPwd;
    private String email;
}
