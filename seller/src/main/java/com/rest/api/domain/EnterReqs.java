package com.rest.api.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "EnterReqsBuilder")
@Getter
@Table(name = "enter_reqs")
public class EnterReqs {
    @Id
    @Column(name = "enterReqId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enterReqId;

    @JoinColumn(name = "seller")
    private Long sellerId;  // seller와 매핑

    @Column(nullable = false) private String storeName;
    @Column(nullable = false) private String storeAddress;
    @Column(nullable = false) private String crNumber;
    @Column(nullable = false, columnDefinition = "boolean default false") private Boolean isAccepted;

    public EnterReqsBuilder builder(Long enterReqId) {
        if(enterReqId == null) {
            throw new IllegalArgumentException("필수 파라미터(입점신청 ID) 누락");
        }
        return EnterReqsBuilder().enterReqId(enterReqId);
    }

}
