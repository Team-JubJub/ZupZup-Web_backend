package com.rest.api.service.base;


import com.rest.api.model.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseService<E extends BaseEntity, Rq, Rs, R extends JpaRepository<E, Long>> {

    List<Rs> findAll() throws Exception; // 전체 찾기
    Rs find(Long id) throws Exception; // 단건 찾기
    Rs save(Rq rq) throws Exception; // 관리자 저장
    Rs update(Long id, Rq rq) throws Exception; // 관리자 업데이트
    Long delete(Long id) throws Exception; // 관리자 삭제
}
