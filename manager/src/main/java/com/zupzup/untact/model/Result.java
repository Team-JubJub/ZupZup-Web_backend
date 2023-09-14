package com.zupzup.untact.model;

import com.zupzup.untact.model.dto.request.ResultReq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Where(clause = "is_deleted IS false")
public class Result extends BaseEntity {

    // 승인여부
    @Column
    private Boolean result;

    // store entity 용 값들
    @Column
    private String storeName; // 가게이름
    @Column
    private String storeImageUrl; // 가게 대표 이미지 url - 이미지 없을 시 기본이미지
    @Column
    private String storeAddress; // 가게 주소
    @Column
    private String category; // 카테고리
    @Column
    private String contact; // 연락처
    @Column
    private Double longitude;   // 경도
    @Column
    private Double latitude;    // 위도
    @Column
    private String openTime; // 운영 시작 시간
    @Column
    private String closeTime; // 운영 마감 시간
    @Column
    private String saleTimeStart;   // 할인 시작 시간
    @Column
    private String saleTimeEnd; // 할인 마감 시간
    @Column
    private String saleMatters; // 공지사항
    @Column
    private Boolean isOpen; // 가게 운영 여부
    @Column
    private String closedDay; // 휴무일 (0-휴무, 1-영업)

    public void updateResult(ResultReq rq) {
        this.result = rq.getResult();
        this.storeName = rq.getStoreName();
        this.storeImageUrl = rq.getStoreImageUrl();
        this.storeAddress = rq.getStoreAddress();
        this.category = rq.getCategory();
        this.contact = rq.getContact();
        this.longitude = rq.getLongitude();
        this.latitude = rq.getLatitude();
        this.openTime = rq.getOpenTime();
        this.closeTime = rq.getCloseTime();
        this.saleTimeStart = rq.getSaleTimeStart();
        this.saleTimeEnd = rq.getSaleTimeEnd();
        this.saleMatters = rq.getSaleMatters();
        this.isOpen = rq.getIsOpen();
        this.closedDay = rq.getClosedDay();
    }
}
