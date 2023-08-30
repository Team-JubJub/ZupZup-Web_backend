package com.rest.api.model;

import com.zupzup.untact.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "EnterReqsBuilder")
@Getter
@Table(name = "enter_reqs")
public class EnterReqs extends BaseEntity {    // 사장님이 입점등록 시 사용되는 entity

    @Column(nullable = false) private String storeName;
    @Column(nullable = false) private String storeAddress;
    @Column(nullable = false) private String crNumber;  // 사업자 등록 번호
    @Column(nullable = false, columnDefinition = "boolean default false") private Boolean isAccepted;   // 줍줍 매니저가 승인했는지 여부

}
