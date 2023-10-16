package com.zupzup.untact.service.impl;

import com.zupzup.untact.domain.auth.Role;
import com.zupzup.untact.domain.auth.Seller.Seller;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static com.zupzup.untact.exception.member.MemberExceptionType.ALREADY_EXIST_USERNAME;

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
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;

    /**
     * 아이디 중복 확인
     */
    @Override
    public String checkLoginId(String loginId) {

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

            // 존재하면 rs 전송
            MemberRes rs = new MemberRes();
            rs.setLoginId("이미 존재하는 회원입니다.");

            return rs;
        }

        // 비밀번호 동일 여부 확인
        if (!rq.getLoginPwd1().equals(rq.getLoginPwd2())) {

            // 같지 않으면 rs 전송
            MemberRes rs = new MemberRes();
            rs.setLoginId("비밀번호가 같지 않습니다.");

            return rs;
        }

        // 패스워드 인코딩 후 저장
        Member m = Member.builder()
                .created_at(timeSetter())
                .name(rq.getName())
                .phoneNum(rq.getPhoneNum())
                .ad(rq.getAd())
                .loginId(rq.getLoginId())
                .loginPwd(passwordEncoder.encode(rq.getLoginPwd1()))
                .email(rq.getEmail())
                .cnt(0)
                .build();

        m.setRoles(
                Collections.singletonList(Authority.builder()
                        .name("ROLE_SELLER")
                        .member(m)
                        .build())

        );

        // Seller 엔티티에도 저장
        Seller s = new Seller();
        s.setName(rq.getName());
        s.setLoginId(rq.getLoginId());
        s.setLoginPwd(passwordEncoder.encode(rq.getLoginPwd1()));
        s.setEmail(rq.getEmail());
        s.setPhoneNumber(rq.getPhoneNum());
        s.setRole(Role.ROLE_SELLER);

        sellerRepository.save(s);

        m.setSellerId(s.getSellerId());
        memberRepository.save(m);

        // 저장후 response 형식에 맞춰 값 반환
        return modelMapper.map(m, MemberRes.class);

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
