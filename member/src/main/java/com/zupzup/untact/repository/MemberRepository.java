package com.zupzup.untact.repository;

import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.request.MemberReq;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends BaseRepository<Member> {

    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByNameAndPhoneNum(String name, String phoneNum);
}