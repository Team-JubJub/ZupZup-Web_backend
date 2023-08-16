package com.rest.api.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {

    private String loginId;
    private String loginPwd;
}
