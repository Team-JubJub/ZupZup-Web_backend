package com.zupzup.untact.service.impl;

import com.zupzup.untact.exception.MemberException;
import com.zupzup.untact.exception.MemberExceptionType;
import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.request.MemberReq;
import com.zupzup.untact.model.response.MemberRes;
import com.zupzup.untact.repository.MemberRepository;
import com.zupzup.untact.service.BaseServiceImpl;
import com.zupzup.untact.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_USERNAME);
        }

        return "사용 가능한 아이디입니다.";
    }

    /**
     * 회원가입
     */
    @Override
    public MemberRes save(MemberReq rq) throws Exception {

            // 패스워드 인코딩 후 저장
            Member m = new Member();
            m.updateMember(rq, passwordEncoder);
            memberRepository.save(m);

            // 저장후 response 형식에 맞춰 값 반환
            return modelMapper.map(m, MemberRes.class);

    }
}
