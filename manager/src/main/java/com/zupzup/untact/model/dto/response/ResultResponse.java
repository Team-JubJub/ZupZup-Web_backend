package com.zupzup.untact.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResultResponse {

    // storeId
    private Long id;

    // 승인여부
    private Boolean result;

    // seller entity 용 로그인, 비밀번호 값
    private String loginId;
    private String loginPwd;
    private String role;

    // store entity 용 값들
    private String storeName; // 가게이름
    private String storeImageUrl; // 가게 대표 이미지 url - 이미지 없을 시 기본이미지
    private String storeAddress; // 가게 주소
    private String category; // 카테고리
    private String contact; // 연락처
    private Double longitude;   // 경도
    private Double latitude;    // 위도
    private String openTime; // 운영 시작 시간
    private String closeTime; // 운영 마감 시간
    private String saleTimeStart;   // 할인 시작 시간
    private String saleTimeEnd; // 할인 마감 시간
    private String saleMatters; // 공지사항
    private Boolean isOpen; // 가게 운영 여부
    private String closedDay; // 휴무일 (0-휴무, 1-영업)
}
