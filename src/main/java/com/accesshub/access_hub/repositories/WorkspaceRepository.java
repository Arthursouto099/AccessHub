package com.accesshub.access_hub.repositories;

import com.accesshub.access_hub.entities.Workspace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace,UUID> {
    Page<Workspace> findByCreator_idUser(UUID idUser, Pageable pageable);
}
