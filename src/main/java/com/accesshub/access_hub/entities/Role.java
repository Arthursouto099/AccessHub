package com.accesshub.access_hub.entities;

import com.accesshub.access_hub.enums.Roles;
import jakarta.persistence.*;
import java.util.UUID;
@Entity
@Table(name = "tb_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  UUID idRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "name")
    private Roles name;

    protected  Role() {}

    public Role(Roles name) {
        this.name = name;
    }

    public UUID getIdRole() {
        return idRole;
    }

    public void setIdRole(UUID idRole) {
        this.idRole = idRole;
    }

    public Roles getName() {
        return name;
    }

    public void setName(Roles name) {
        this.name = name;
    }
}
