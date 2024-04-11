package com.zupzup.untact.repository;

import com.zupzup.untact.model.domain.enums.EnterState;
import com.zupzup.untact.model.Enter;
import com.zupzup.untact.model.Member;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterRepository extends BaseRepository<Enter> {

    List<Enter> findByState(EnterState enterState); // 상태 조회

    @Query("SELECT e FROM Enter e WHERE e.storeName LIKE %:storeName% AND e.state = :state")
    List<Enter> searchByStoreNameContainingAndState(@Param("storeName") String storeName, @Param("state") EnterState state); // 매장명 검색

    // Member 로 삭제
    void deleteByMember(Member member);
}
