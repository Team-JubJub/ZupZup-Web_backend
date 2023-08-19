package com.rest.api.controller.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface BaseController<Rq, Rs> {

    ResponseEntity<Rs> save(@RequestBody Rq request);   // 저장
    ResponseEntity<Object> findAll(); // 전체 조회
    ResponseEntity<Rs> update(@RequestBody Rq request); // 업데이트
    ResponseEntity<Long> delete(@PathVariable Long id); // 삭제

}
