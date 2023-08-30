package com.zupzup.untact.api.impl;

import com.zupzup.untact.api.BaseControllerImpl;
import com.zupzup.untact.api.LoginController;
import com.zupzup.untact.model.Login;
import com.zupzup.untact.model.request.LoginReq;
import com.zupzup.untact.model.response.LoginRes;
import com.zupzup.untact.repository.LoginRepository;
import com.zupzup.untact.service.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class LoginControllerImpl extends BaseControllerImpl<Login, LoginReq, LoginRes, LoginRepository> implements LoginController {

    public LoginControllerImpl(BaseService<Login, LoginReq, LoginRes, LoginRepository> baseService) {
        super(baseService);
    }
}
