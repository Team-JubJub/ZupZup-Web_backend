package com.zupzup.untact.model.dto.request;

import com.zupzup.untact.domain.enums.StoreCategory;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreUpdateReq {

    private String storeContact;
    private String storeName;
    private String storeAddress;
    private String category;
}
