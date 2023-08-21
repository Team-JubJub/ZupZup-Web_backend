package com.rest.api.repository.base;

import com.rest.api.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean   // 실제 사용되는 Repository가 아님을 표시, bean을 만들지 않도록 함.
public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, Long> {
}
