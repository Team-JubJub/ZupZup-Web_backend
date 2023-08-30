package com.zupzup.untact.service.impl;

import com.zupzup.untact.exception.LoginException;
import com.zupzup.untact.exception.LoginExceptionType;
import com.zupzup.untact.model.Login;
import com.zupzup.untact.model.request.LoginReq;
import com.zupzup.untact.model.response.LoginRes;
import com.zupzup.untact.repository.LoginRepository;
import com.zupzup.untact.service.BaseServiceImpl;
import com.zupzup.untact.service.LoginService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends BaseServiceImpl<Login, LoginReq, LoginRes, LoginRepository> implements LoginService {

    public LoginServiceImpl(LoginRepository repository, LoginRepository loginRepository) {
        super(repository);
        this.loginRepository = loginRepository;
    }

    private final LoginRepository loginRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;

    /**
     * 아이디 중복 확인
     */
    @Override
    public String checkLoginId(String loginId) {

        if (loginRepository.findByLoginId(loginId).isPresent()) {

            // 아이디가 존재하면 예외 발생
            throw new LoginException(LoginExceptionType.ALREADY_EXIST_USERNAME);
        }

        return "사용 가능한 아이디입니다.";
    }

    /**
     * 회원가입
     */
    @Override
    public LoginRes save(LoginReq rq) throws Exception {

        try {

            // 패스워드 인코딩 (암호화)
            String newPwd = passwordEncoder.encode(rq.getLoginPwd());
            rq.setLoginPwd(newPwd);

            Login l = loginRepository.save(modelMapper.map(rq, Login.class));

            // 저장후 response 형식에 맞춰 값 반환
            return modelMapper.map(l, LoginRes.class);

        } catch (Exception e) {

            // 저장에 실패했을 경우 예외 발생
            throw new LoginException(LoginExceptionType.SERVER_ERR);
        }
    }
}
