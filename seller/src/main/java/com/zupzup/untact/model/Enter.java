package com.zupzup.untact.model;

import com.zupzup.untact.model.request.EnterReq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@Where(clause = "is_deleted IS false")
public class Enter extends BaseEntity {

    @ManyToOne
    private Member member;

    // 입점 신청
    @Column private String name; // 대표자명
    @Column private String phoneNum; // 휴대폰 번호
    @Column private String storeName; // 가게 이름
    @Column private String storeAddress; // 가게 주소
    @Column private String crNumber;  // 사업자 등록 번호
    @Column private Boolean isAccepted; // 승인여부

    public void saveEnter(EnterReq rq, Member m) {

        this.member = m;
        this.storeName = rq.getStoreName();
        this.storeAddress = rq.getStoreAddress();
        this.crNumber = rq.getCrNumber();
    }
}
