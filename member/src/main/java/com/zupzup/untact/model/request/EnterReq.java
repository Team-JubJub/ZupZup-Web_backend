package com.zupzup.untact.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EnterReq {

    private Long id; // 신청자 unique Id
    private String name;
    private String phoneNum;
    private String storeName;
    private String storeAddress;
    private String crNumber;
}
