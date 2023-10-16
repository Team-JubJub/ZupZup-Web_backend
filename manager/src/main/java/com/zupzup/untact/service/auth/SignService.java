package com.zupzup.untact.service.auth;

import com.zupzup.untact.config.auth.JwtProvider;
import com.zupzup.untact.exception.member.MemberException;
import com.zupzup.untact.model.Manager;
import com.zupzup.untact.model.dto.request.ManagerLoginReq;
import com.zupzup.untact.model.dto.response.ManagerLoginRes;
import com.zupzup.untact.repository.ManagerRepository;
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

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    /**
     * 로그인
     */
    public ManagerLoginRes login(ManagerLoginReq rq) throws Exception {

        Manager manager = managerRepository.findByLoginId(rq.getLoginId())
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        if (!passwordEncoder.matches(rq.getLoginPwd(), manager.getLoginPwd())) {

            throw new MemberException(WRONG_PASSWORD);
        }

        //res 생성
        ManagerLoginRes res = new ManagerLoginRes();
        res.setLoginId(manager.getLoginId());
        res.setToken(jwtProvider.createToken(manager.getLoginId(), manager.getRoles()));

        return res;

    }
}
