package com.zupzup.untact.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreUpdateReq {

    private String storeContact;
    private String storeName;
    private String storeAddress;
    private String category;
}
