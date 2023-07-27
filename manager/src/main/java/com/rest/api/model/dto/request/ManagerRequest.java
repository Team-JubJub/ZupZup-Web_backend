package com.rest.api.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ManagerRequest {

    private String name;
    private String loginId;
    private String loginPwd;
}
