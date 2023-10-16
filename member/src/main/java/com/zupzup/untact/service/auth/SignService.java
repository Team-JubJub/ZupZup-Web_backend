package com.zupzup.untact.service.auth;

import com.zupzup.untact.config.auth.JwtProvider;
import com.zupzup.untact.exception.member.MemberException;
import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.request.MemberFindReq;
import com.zupzup.untact.model.request.MemberLoginReq;
import com.zupzup.untact.model.request.MemberPwdReq;
import com.zupzup.untact.model.response.MemberLoginRes;
import com.zupzup.untact.model.response.MemberRes;
import com.zupzup.untact.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zupzup.untact.exception.member.MemberExceptionType.NOT_FOUND_MEMBER;
import static com.zupzup.untact.exception.member.MemberExceptionType.WRONG_PASSWORD;

@Service
@Transactional
@RequiredArgsConstructor
public class SignService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    /**
     * 로그인
     */
    public MemberLoginRes login(MemberLoginReq rq) throws Exception {

        Member member = memberRepository.findByLoginId(rq.getLoginId())
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        if (!passwordEncoder.matches(rq.getLoginPwd(), member.getLoginPwd())) {

            throw new MemberException(WRONG_PASSWORD);
        }

        //res 생성
        MemberLoginRes res = new MemberLoginRes();
        res.setLoginId(member.getLoginId());
        res.setToken(jwtProvider.createToken(member.getLoginId(), member.getRoles()));

        return res;

    }

    /**
     * 비밀번호 수정
     */
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
     * 아이디 찾기
     */
    public MemberRes findLoginId(MemberFindReq rq) {

        Member m = memberRepository.findByNameAndPhoneNum(rq.getName(), rq.getPhoneNum())
                // 회원을 찾지 못하면 에러 전송
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        // rs 값 설정
        MemberRes rs = new MemberRes();
        rs.setId(m.getId());
        rs.setLoginId(m.getLoginId());
        rs.setCreated_at(m.getCreated_at());

        return rs;
    }
}
