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

    // 아이디와 전화번호로 검증
    Optional<Member> findByLoginIdAndPhoneNum(String loginId, String phoneNum);

    // Seller unique ID로 삭제
    void deleteBySellerId(Long sellerId);
    // Seller unique ID로 찾기
    Optional<Member> findBySellerId(Long sellerId);
}
