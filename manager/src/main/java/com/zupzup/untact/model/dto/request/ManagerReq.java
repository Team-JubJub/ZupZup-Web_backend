package com.zupzup.untact.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ManagerReq {

    private Long id;
    private String name;
    private String loginId;
    private String loginPwd;
}
