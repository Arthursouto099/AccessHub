package com.accesshub.access_hub.dtos;

import com.accesshub.access_hub.entities.Workspace;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorkspaceUpdateDto(
        @NotBlank
        @Size(min = 2, max = 60)
        String name
) {}
