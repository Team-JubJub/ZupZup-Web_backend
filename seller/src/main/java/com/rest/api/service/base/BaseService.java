package com.rest.api.service.base;

import com.rest.api.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseService<E extends BaseEntity, Rq, Rs, R extends JpaRepository<E, Long>> {

    List<Rs> findAllManger() throws Exception; // 사장님 전체 조회
    Rs save(Rq rq) throws Exception; // 사장님 저장
    Rs update(Rq rq) throws Exception; // 사장님 업데이트
    Long delete(Long id) throws Exception; // 사장님 삭제

}
