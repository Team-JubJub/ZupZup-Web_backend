package com.zupzup.untact.repository;

import com.zupzup.untact.domain.enums.EnterState;
import com.zupzup.untact.model.Enter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterRepository extends BaseRepository<Enter> {

    List<Enter> findByState(EnterState enterState);
}
