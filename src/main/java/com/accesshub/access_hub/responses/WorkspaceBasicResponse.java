package com.accesshub.access_hub.responses;

import com.accesshub.access_hub.entities.Workspace;

import java.time.LocalDateTime;
import java.util.UUID;

public record WorkspaceBasicResponse(
        UUID idWorkspace,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public  static WorkspaceBasicResponse toEntity(Workspace ws) {
        return new WorkspaceBasicResponse(ws.getIdWorkspace(), ws.getName(), ws.getCreatedAt(), ws.getUpdatedAt());
    }
}
