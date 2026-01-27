package com.accesshub.access_hub.repositories;

import com.accesshub.access_hub.entities.Role;
import com.accesshub.access_hub.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(Roles name);
}
