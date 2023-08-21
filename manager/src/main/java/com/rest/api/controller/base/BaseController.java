package com.rest.api.controller.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BaseController<Rq, Rs> {

    ResponseEntity<Rs> save(@RequestBody Rq request);
    ResponseEntity<Object> findAll();
    ResponseEntity<Rs> find(@PathVariable Long id);
    ResponseEntity<Rs> update(@PathVariable Long id, @RequestBody Rq request);
    ResponseEntity<Long> delete(@PathVariable Long id);
}
