package com.zupzup.untact.repository;

import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.request.MemberReq;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends BaseRepository<Member> {

    UserDetails findLoginSellerByLoginId(String loginId);

    Optional<Member> findByLoginId(String loginId);
}
