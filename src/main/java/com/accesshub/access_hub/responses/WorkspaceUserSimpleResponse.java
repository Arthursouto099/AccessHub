package com.accesshub.access_hub.responses;

import com.accesshub.access_hub.entities.WorkspaceUser;
import com.accesshub.access_hub.enums.WorkspaceRoles;

import java.time.LocalDateTime;
import java.util.UUID;

public record WorkspaceUserSimpleResponse(
        UUID idWorkspaceUser,
        String username,
        String email,
        LocalDateTime joinedAt,
        WorkspaceRoles role
) {
    public static WorkspaceUserSimpleResponse fromEntity(WorkspaceUser wsu) {
        return new WorkspaceUserSimpleResponse(
                wsu.getIdWorkspaceUser(),
                wsu.getUser().getUsername(),
                wsu.getUser().getEmail(),
                wsu.getJoinedAt(),
                wsu.getRole()
        );
    }
}
