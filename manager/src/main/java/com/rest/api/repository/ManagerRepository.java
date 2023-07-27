package com.rest.api.repository;

import com.rest.api.model.Manager;
import com.rest.api.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends BaseRepository<Manager> {
    Manager findByName(String name);
}
