package com.zupzup.untact.service.impl;

import com.zupzup.untact.exception.CustomErrorCode;
import com.zupzup.untact.exception.CustomException;
import com.zupzup.untact.jwt.JwtTokenProvider;
import com.zupzup.untact.model.Manager;
import com.zupzup.untact.model.dto.request.LoginReq;
import com.zupzup.untact.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String login(LoginReq loginReq) {

        // 로그인 시 member 찾을 수 있으면 정보 가져옴
        Manager manager = managerRepository.findByLoginId(loginReq.getLoginId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MANAGER_NOT_FOUND));

        // 패스워드 불일치 하면 에러 발생
        if (!passwordEncoder.matches(loginReq.getLoginPwd(), manager.getLoginPwd())) {
            throw new CustomException(CustomErrorCode.PASSWORD_NOT_SAME);
        }

        // Access Token 발급
        return jwtTokenProvider.createToken(manager.getLoginId(), manager.getRoles());
    }
}
