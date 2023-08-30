package com.zupzup.untact.service;

import com.zupzup.untact.model.Login;
import com.zupzup.untact.model.request.LoginReq;
import com.zupzup.untact.model.response.LoginRes;
import com.zupzup.untact.repository.LoginRepository;

public interface LoginService extends BaseService<Login, LoginReq, LoginRes, LoginRepository> {

    String checkLoginId(String loginId);
}
