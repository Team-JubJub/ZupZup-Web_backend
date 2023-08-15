package com.rest.api.service.impl;

import com.rest.api.exception.CustomErrorCode;
import com.rest.api.exception.CustomException;
import com.rest.api.jwt.JwtTokenProvider;
import com.rest.api.model.Manager;
import com.rest.api.model.dto.request.LoginRequest;
import com.rest.api.repository.ManagerRepository;
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
    public String login(LoginRequest loginReq) {

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
