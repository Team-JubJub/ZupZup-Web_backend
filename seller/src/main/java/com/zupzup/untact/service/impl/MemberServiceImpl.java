package com.zupzup.untact.service.impl;

import com.zupzup.untact.exception.member.MemberException;
import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.request.MemberPwdReq;
import com.zupzup.untact.model.request.MemberReq;
import com.zupzup.untact.model.response.MemberRes;
import com.zupzup.untact.repository.MemberRepository;
import com.zupzup.untact.service.BaseServiceImpl;
import com.zupzup.untact.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.zupzup.untact.exception.member.MemberExceptionType.ALREADY_EXIST_USERNAME;
import static com.zupzup.untact.exception.member.MemberExceptionType.NOT_FOUND_MEMBER;

@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, MemberReq, MemberRes, MemberRepository> implements MemberService {

    public MemberServiceImpl(MemberRepository repository, MemberRepository memberRepository) {
        super(repository);
        this.memberRepository = memberRepository;
    }

    private final MemberRepository memberRepository;

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
     * 비밀번호 수정
     */
    @Override
    public String changePwd(Long id, MemberPwdReq rq) {

        // 비밀번호 동일 여부 확인
        if (!rq.getLoginPwd1().equals(rq.getLoginPwd2())) {

            // 같지 않으면 rs 전송
            return "Not same password";
        }

        Member m = memberRepository.findById(id)
                // 회원을 찾지 못하면 에러 전송
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
        m.changePwd(rq.getLoginPwd2(), passwordEncoder);

        return "Password Changed";
    }

    /**
     * 회원가입
     */
    @Override
    public MemberRes save(MemberReq rq) {

        // 비밀번호 동일 여부 확인
        if (!rq.getLoginPwd1().equals(rq.getLoginPwd2())) {

            // 같지 않으면 rs 전송
            MemberRes rs = new MemberRes();
            rs.setLoginId("비밀번호가 같지 않습니다.");

            return rs;
        }

        // 패스워드 인코딩 후 저장
        Member m = new Member();
        m.updateMember(rq, passwordEncoder);
        memberRepository.save(m);

        // 저장후 response 형식에 맞춰 값 반환
        return modelMapper.map(m, MemberRes.class);

    }
}
