package com.rest.api.model;

import com.rest.api.model.base.BaseEntity;
import com.rest.api.model.dto.request.ManagerRequest;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@Where(clause = "is_deleted IS false")
public class Manager extends BaseEntity {

    private String name;
    private String loginId;
    private String loginPwd;

    public void updateManager(ManagerRequest rq) {
        this.name = rq.getName();
        this.loginId = rq.getLoginId();
        this.loginPwd = rq.getLoginPwd();
    }
}
