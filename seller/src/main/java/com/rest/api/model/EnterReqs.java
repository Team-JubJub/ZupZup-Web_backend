package com.rest.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "EnterReqsBuilder")
@Getter
@Table(name = "enter_reqs")
public class EnterReqs extends BaseEntity {    // 사장님이 입점등록 시 사용되는 entity
    @Id
    @Column(name = "enterReqId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enterReqId;

    @JoinColumn(name = "seller")
    private Long sellerId;  // seller와 매핑

    @Column(nullable = false) private String storeName;
    @Column(nullable = false) private String storeAddress;
    @Column(nullable = false) private String crNumber;  // 사업자 등록 번호
    @Column(nullable = false, columnDefinition = "boolean default false") private Boolean isAccepted;   // 줍줍 매니저가 승인했는지 여부

    public EnterReqsBuilder builder(Long enterReqId) {
        if(enterReqId == null) {
            throw new IllegalArgumentException("필수 파라미터(입점신청 ID) 누락");
        }
        return EnterReqsBuilder().enterReqId(enterReqId);
    }

}
