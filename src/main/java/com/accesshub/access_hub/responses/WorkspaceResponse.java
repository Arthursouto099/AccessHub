package com.accesshub.access_hub.responses;

import com.accesshub.access_hub.entities.Workspace;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record WorkspaceResponse(
        UUID idWorkspace,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<WorkspaceUserSimpleResponse> workspaceUsers
) {
    public static WorkspaceResponse fromEntity(Workspace ws) {
        return  new WorkspaceResponse(
                ws.getIdWorkspace(),
                ws.getName(),
                ws.getCreatedAt(),
                ws.getUpdatedAt(),
                ws.getWorkspaceUsers().stream().map(WorkspaceUserSimpleResponse::fromEntity).toList()
        );
    }
}
