package com.zupzup.untact.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConfirmStoreListRes {

    private String sellerName; // 판매자 이름
    private String storeName; // 가게 이름
    private String confirmStatusTimestamp; // confirm 으로 변경된 시간
}
