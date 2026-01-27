package com.accesshub.access_hub.repositories;

import com.accesshub.access_hub.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoty extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);
}
