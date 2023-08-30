package com.zupzup.untact.repository;

import com.zupzup.untact.model.Login;
import com.zupzup.untact.model.request.LoginReq;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends BaseRepository<Login> {

    UserDetails findLoginSellerByLoginId(String loginId);

    Optional<LoginReq> findByLoginId(String loginId);
}
