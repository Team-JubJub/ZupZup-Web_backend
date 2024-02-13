package com.zupzup.untact.service.impl;

import com.zupzup.untact.domain.auth.Role;
import com.zupzup.untact.domain.auth.seller.Seller;
import com.zupzup.untact.exception.member.MemberException;
import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.auth.Authority;
import com.zupzup.untact.model.request.MemberReq;
import com.zupzup.untact.model.response.MemberRes;
import com.zupzup.untact.repository.MemberRepository;
import com.zupzup.untact.repository.SellerRepository;
import com.zupzup.untact.service.BaseServiceImpl;
import com.zupzup.untact.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static com.zupzup.untact.exception.member.MemberExceptionType.*;

@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, MemberReq, MemberRes, MemberRepository> implements MemberService {

    public MemberServiceImpl(MemberRepository repository, MemberRepository memberRepository, SellerRepository sellerRepository) {
        super(repository);
        this.memberRepository = memberRepository;
        this.sellerRepository = sellerRepository;
    }

    private final MemberRepository memberRepository;
    private final SellerRepository sellerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;

    /**
     * 아이디 중복 확인
     */
    @Override
    public String checkDuplicateLoginId(String loginId) {

        if (memberRepository.findByLoginId(loginId).isPresent()) {

            // 아이디가 존재하면 예외 발생
            throw new MemberException(ALREADY_EXIST_USERNAME);
        }
        return "Username is Available";
    }

    /**
     * 회원가입
     */
    @Override
    public MemberRes save(MemberReq rq) {

        // 이름과 전화번호가 같은 사람이 있는지 확인
        if (memberRepository.existsByNameAndPhoneNum(rq.getName(), rq.getPhoneNum())) {

            throw new MemberException(ALREADY_EXIST_MEMBER);
        }

        // 비밀번호 동일 여부 확인
        if (!rq.getLoginPwd1().equals(rq.getLoginPwd2())) {

            throw new MemberException(NOT_SAME_PASSWORD);
        }

        // 패스워드 인코딩 후 저장
        Member member = Member.builder()
                .created_at(timeSetter())
                .name(rq.getName())
                .phoneNum(rq.getPhoneNum())
                .ad(rq.getAd())
                .loginId(rq.getLoginId())
                .loginPwd(passwordEncoder.encode(rq.getLoginPwd1()))
                .email(rq.getEmail())
                .cnt(0)
                .build();

        member.setRoles(
                Collections.singletonList(Authority.builder()
                        .name("ROLE_SELLER")
                        .member(member)
                        .build())

        );

        // Seller 엔티티에도 저장
        Seller seller = new Seller();
        seller.setName(rq.getName());
        seller.setLoginId(rq.getLoginId());
        seller.setLoginPwd(passwordEncoder.encode(rq.getLoginPwd1()));
        seller.setEmail(rq.getEmail());
        seller.setPhoneNumber(rq.getPhoneNum());
        seller.setRole(Role.ROLE_SELLER);
        seller.setWantDeletion(false);

        sellerRepository.save(seller);

        seller.setSellerId(seller.getSellerId());
        memberRepository.save(member);

        // 저장후 response 형식에 맞춰 값 반환
        return modelMapper.map(member, MemberRes.class);

    }

    //-----------------------------
    /**
     * 시간 포매팅
     */
    private String timeSetter() {

        ZonedDateTime nowTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedOrderTime = nowTime.format(formatter);

        return formattedOrderTime;
    }
}
