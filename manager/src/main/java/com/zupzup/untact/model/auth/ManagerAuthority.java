package com.zupzup.untact.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zupzup.untact.model.Manager;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerAuthority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String name;

    @JoinColumn(name = "manager")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Manager manager;

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
