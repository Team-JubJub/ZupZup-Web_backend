package com.zupzup.untact.service.auth;

import com.zupzup.untact.config.auth.JwtProvider;
import com.zupzup.untact.exception.ManagerException;
import com.zupzup.untact.model.Manager;
import com.zupzup.untact.model.dto.request.ManagerLoginReq;
import com.zupzup.untact.model.dto.response.ManagerLoginRes;
import com.zupzup.untact.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zupzup.untact.exception.ManagerExceptionType.MANAGER_NOT_FOUND;
import static com.zupzup.untact.exception.ManagerExceptionType.PASSWORD_NOT_SAME;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagerSignService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    /**
     * 로그인
     */
    public ManagerLoginRes login(ManagerLoginReq rq) throws Exception {

        Manager manager = managerRepository.findByLoginId(rq.getLoginId())
                .orElseThrow(() -> new ManagerException(MANAGER_NOT_FOUND));

        if (!passwordEncoder.matches(rq.getLoginPwd(), manager.getLoginPwd())) {

            throw new ManagerException(PASSWORD_NOT_SAME);
        }

        //res 생성
        ManagerLoginRes res = new ManagerLoginRes();
        res.setLoginId(manager.getLoginId());
        res.setToken(jwtProvider.createToken(manager.getLoginId(), manager.getRoles()));

        return res;

    }
}
