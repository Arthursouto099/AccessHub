package com.accesshub.access_hub.repositories;

import com.accesshub.access_hub.entities.WorkspaceUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, UUID> {

}
