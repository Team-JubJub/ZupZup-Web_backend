package com.zupzup.untact.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreRes {

    private Long storeId;
    private String sellerName;
    private String sellerLoginId;
    private String storeContact;
    private String crNumber;
    private String storeAddress;
    private String storeImageUrl;
    private String category;
}
