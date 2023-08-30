package com.zupzup.untact.repository;

import com.zupzup.untact.model.Manager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends BaseRepository<Manager> {

    Optional<Manager> findByLoginId(String loginId);
}
