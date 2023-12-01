package com.zupzup.untact.model;

import com.zupzup.untact.domain.enums.EnterState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@Getter @Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Enter extends BaseEntity {

    @ManyToOne
    private Member member;

    // 입점 신청
    @Column(nullable = false) private String name; // 대표자명
    @Column(nullable = false) private String phoneNum; // 사장님 휴대폰 번호
    @Column(nullable = false) private String storeNum; // 가게 전화번호
    @Column(nullable = false) private String storeName; // 가게 이름
    @Column(nullable = false) private String storeAddress; // 가게 주소
    @Column(nullable = false) private String crNumber;  // 사업자 등록 번호
    @Column(nullable = false) private Double longitude; // 경도
    @Column(nullable = false) private Double latitude; // 위도
    @Column private Boolean isAccepted; // 승인여부

    // 상태 설정
    @Enumerated(EnumType.STRING)
    @Column private EnterState state; // 문의 상태(기본 신규 신청으로 설정)

}
