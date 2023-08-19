package com.rest.api.service.base;

import com.rest.api.model.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public class BaseServiceImpl<E extends BaseEntity, Rq, Rs, R extends JpaRepository<E, Long>> implements BaseService<E, Rq, Rs, R> {

    private final R repository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List findAllManger() throws Exception {
        return null;
    }

    @Override
    public Object save(Object o) throws Exception {
        return null;
    }

    @Override
    public Object update(Object o) throws Exception {
        return null;
    }

    @Override
    public Long delete(Long id) throws Exception {
        return null;
    }
}
