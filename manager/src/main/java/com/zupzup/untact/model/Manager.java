package com.zupzup.untact.model;

import com.zupzup.untact.model.dto.request.ManagerRequest;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@Where(clause = "is_deleted IS false")
public class Manager extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String loginId;
    @Column(nullable = false)
    private String loginPwd;

    @ElementCollection(fetch = FetchType.EAGER)
    @Getter
    private List<String> roles = new ArrayList<>();

    public void updateManager(ManagerRequest rq, PasswordEncoder encoder) {
        this.name = rq.getName();
        this.loginId = rq.getLoginId();
        this.loginPwd = encoder.encode(rq.getLoginPwd());
    }
}
