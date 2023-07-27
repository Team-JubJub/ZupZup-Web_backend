package com.rest.api.controller.base;

import com.rest.api.model.base.BaseEntity;
import com.rest.api.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
@ResponseBody
@RequiredArgsConstructor
public class BaseControllerImpl<E extends BaseEntity, Rq, Rs, R extends JpaRepository<E, Long>> implements BaseController<Rq, Rs> {

    private final BaseService<E, Rq, Rs, R> baseService;

    @Override
    @PostMapping
    public ResponseEntity<Rs> save(Rq request) {
        try {
            Rs rs = baseService.save(request);
            return new ResponseEntity<>(rs, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<Object> findAllManager() {
        try {
            List<Rs> rsList = baseService.findAllManger();
            return new ResponseEntity<>(rsList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PatchMapping
    public ResponseEntity<Rs> update(Rq request) {
        try {
            Rs rs = baseService.update(request);
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        try {
            Long rs = baseService.delete(id);
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
