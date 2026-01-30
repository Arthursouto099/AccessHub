package com.accesshub.access_hub.controllers;

import com.accesshub.access_hub.dtos.WorkspaceCreateDto;
import com.accesshub.access_hub.dtos.WorkspaceUpdateDto;
import com.accesshub.access_hub.entities.User;
import com.accesshub.access_hub.entities.Workspace;
import com.accesshub.access_hub.responses.WorkspaceBasicResponse;
import com.accesshub.access_hub.responses.WorkspaceResponse;
import com.accesshub.access_hub.services.WorkspaceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/features/manager")
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping("/my/workspaces")
    public ResponseEntity<Page<WorkspaceResponse>> myWorkspaces(@AuthenticationPrincipal User user, @PageableDefault(size = 20) Pageable pageable) {
        return  ResponseEntity.status(HttpStatus.OK).body(workspaceService.myWorkspaces(user, pageable));
    }

    @PostMapping("/create/workspace")
    public ResponseEntity<?> createWorkspace(@AuthenticationPrincipal User user, @Valid @RequestBody WorkspaceCreateDto dto) {
            Workspace workspace = workspaceService.createWorkspace(WorkspaceCreateDto.toEntity(dto), user);
            return  ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "idWorkSpace", workspace.getIdWorkspace() ,
                    "name", workspace.getName(),
                    "creator", Map.of(
                            "idUser", workspace.getCreator().getIdUser(),
                            "email", workspace.getCreator().getEmail()
                    )
            ));
    }

    @PatchMapping("/edit/workspace/{idWorkspace}")
    public  ResponseEntity<WorkspaceBasicResponse> editWorkspace(@PathVariable UUID idWorkspace, @AuthenticationPrincipal User user, @Valid @RequestBody WorkspaceUpdateDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(workspaceService.updateWorkSpace(dto, user, idWorkspace));
    }

}
