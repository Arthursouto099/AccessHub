package com.accesshub.access_hub.dtos;

import com.accesshub.access_hub.entities.Workspace;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorkspaceCreateDto(
        @NotBlank()
        @Size(min = 2, message = "O nome precisa ter no mínimo 2 characters")
        String name
) {
    public static Workspace toEntity(WorkspaceCreateDto dto) {
        Workspace workspace = new Workspace();
        workspace.setName(dto.name);
        return  workspace;
    }
}

