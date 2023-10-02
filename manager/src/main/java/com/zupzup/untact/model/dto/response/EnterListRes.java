package com.zupzup.untact.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class EnterListRes {

    private String name; // 대표자명
    private String storeName; // 가게 이름
    private String created_at; // 생성 시간
}
