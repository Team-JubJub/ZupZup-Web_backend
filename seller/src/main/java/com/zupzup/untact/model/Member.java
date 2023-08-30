package com.zupzup.untact.model;

import com.zupzup.untact.domain.auth.Role;
import com.zupzup.untact.model.request.MemberReq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Where(clause = "is_deleted IS false")
public class Member extends BaseEntity implements UserDetails {

    // 회원가입
    @Column(nullable = false) private String loginId; // 로그인 아이디
    @Column(nullable = false) private String loginPwd; // 로그인 패스워드
    @Column(nullable = false) private String email; // 이메일 주소
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role roles; // 권한

    public void updateMember(MemberReq rq, PasswordEncoder encoder) {

        this.loginId = rq.getLoginId();
        this.loginPwd = encoder.encode(rq.getLoginPwd());
        this.email = rq.getEmail();
        this.roles = Role.ROLE_SELLER; // role_seller 로 지정
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return loginPwd;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
