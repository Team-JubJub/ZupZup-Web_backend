package com.zupzup.untact.service.auth;

import com.zupzup.untact.model.Manager;
import com.zupzup.untact.model.auth.ManagerUserDetails;
import com.zupzup.untact.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final ManagerRepository managerRepository;

    /**
     * 로그인 아이디를 통해 유저 정보 가져 옴
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Manager manager = managerRepository.findByLoginId(loginId).orElseThrow(
                () -> new UsernameNotFoundException("해당 아이디에 해당하는 사용자 정보가 존재하지 않습니다.")
        );

        return new ManagerUserDetails(manager);
    }
}
