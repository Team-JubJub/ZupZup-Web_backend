package com.zupzup.untact.model;

import com.zupzup.untact.model.auth.Authority;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Member extends BaseEntity {

    // 회원가입
    @Column(nullable = false) private String name; // 이름
    @Column(nullable = false) private String phoneNum; // 전화번호
    @Column(nullable = false) private String loginId; // 로그인 아이디
    @Column(nullable = false) private String loginPwd; // 로그인 패스워드
    @Column(nullable = false) private String email; // 이메일 주소
    @Column(nullable = false) private Boolean ad; // 광고성 정보 수신 동의 여부

    @Column private Long sellerId; // seller 저장 후 sellerId 저장
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Role roles; // 권한
    @Builder.Default
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Authority> roles = new ArrayList<>();

    public void changePwd(String loginPwd, PasswordEncoder encoder) {

        this.loginPwd = encoder.encode(loginPwd);
    }
}
