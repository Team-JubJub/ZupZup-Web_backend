package com.zupzup.untact.model;

import com.zupzup.untact.model.auth.ManagerAuthority;
import com.zupzup.untact.model.dto.request.ManagerReq;
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
public class Manager extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String loginId;
    @Column(nullable = false)
    private String loginPwd;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @Getter
//    private List<String> roles = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ManagerAuthority> roles = new ArrayList<>();

    public void updateManager(ManagerReq rq, PasswordEncoder encoder) {
        this.name = rq.getName();
        this.loginId = rq.getLoginId();
        this.loginPwd = encoder.encode(rq.getLoginPwd());
    }
}
