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

    @ManyToOne @JoinColumn(name = "sellerId")
    private Long sellerId;  // seller와 매핑, 1:다 => Store와 마찬가지로 entity 생성해서 매핑관계인 거 나타내줘야 할 듯.
//    @ManyToOne @JoinColumn(name = "sellerId") // 이렇게 돼야 함.
//    private Seller seller;

    @Column(nullable = false) private String storeName;
    @Column(nullable = false) private String storeAddress;
    @Column(nullable = false) private String crNumber;  // 사업자 등록 번호
    @Column(nullable = false, columnDefinition = "boolean default false") private Boolean isAccepted;   // 줍줍 매니저가 승인했는지 여부

    public EnterReqsBuilder builder(Long sellerId) {
        if(sellerId == null) {
            throw new IllegalArgumentException("필수 파라미터(사장님 ID) 누락");
        }
        return EnterReqsBuilder().sellerId(sellerId);
    }

}