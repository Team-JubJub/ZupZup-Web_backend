package com.zupzup.untact.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@Where(clause = "is_deleted IS false")
public class Enter extends BaseEntity {

    @Column(nullable = false) private String storeName; // 가게 이름
    @Column(nullable = false) private String storeAddress; // 가게 주소
    @Column(nullable = false) private String crNumber;  // 사업자 등록 번호
    @Column(nullable = false, columnDefinition = "boolean default false") private Boolean isAccepted;
}
