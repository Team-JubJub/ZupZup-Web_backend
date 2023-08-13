package com.rest.api.model;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)  // 엔티티에 변화가 생길 때 발동되는 이벤트 리스너 / AuditingEntityListener: 객체의 생성, 변경 탐지 listener
@Getter
@MappedSuperclass   // 해당 어노테이션이 작성된 클래스는 테이블로 생성되지 않음. 부모 클래스로서의 역할만 수행
abstract class BaseEntity { // 공통으로 쓰이는 필드 선언용 abstract entity

}
