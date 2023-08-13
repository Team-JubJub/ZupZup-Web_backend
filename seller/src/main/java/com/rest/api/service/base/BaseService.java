package com.rest.api.service.base;

import com.rest.api.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseService<E extends BaseEntity, Rq, Rs, R extends JpaRepository<E, Long>> {


}
