package com.zupzup.untact.repository;

import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.request.MemberReq;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends BaseRepository<Member> {

    Optional<Member> findByLoginId(String loginId);

    // 이름과 전화번호 검색했을 때 존재하는지
    Optional<Member> findByNameAndPhoneNum(String name, String phoneNum);
    boolean existsByNameAndPhoneNum(String name, String phoneNum);
}
