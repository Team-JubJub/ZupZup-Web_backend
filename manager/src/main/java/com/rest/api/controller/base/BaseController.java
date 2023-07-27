package com.rest.api.controller.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface BaseController<Rq, Rs> {

    ResponseEntity<Rs> save(@RequestBody Rq request);
    ResponseEntity<Object> findAllManager();
    ResponseEntity<Rs> update(@RequestBody Rq request);
    ResponseEntity<Long> delete(@PathVariable Long id);
}
