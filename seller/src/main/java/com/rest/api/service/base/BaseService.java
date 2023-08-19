package com.rest.api.service.base;

import com.rest.api.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseService<E extends BaseEntity, Rq, Rs, R extends JpaRepository<E, Long>> {


    Rs save(Rq rq) throws Exception; // 저장
    List<Rs> findAll() throws Exception; // 전체 조회
    Rs update(Rq rq) throws Exception; // 업데이트
    Long delete(Long id) throws Exception; // 삭제

}
