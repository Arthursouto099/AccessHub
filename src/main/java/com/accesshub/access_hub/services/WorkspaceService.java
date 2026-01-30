package com.accesshub.access_hub.services;

import com.accesshub.access_hub.dtos.WorkspaceUpdateDto;
import com.accesshub.access_hub.entities.User;
import com.accesshub.access_hub.entities.Workspace;
import com.accesshub.access_hub.entities.WorkspaceUser;
import com.accesshub.access_hub.enums.WorkspaceRoles;
import com.accesshub.access_hub.repositories.UserRepositoty;
import com.accesshub.access_hub.repositories.WorkspaceRepository;
import com.accesshub.access_hub.responses.WorkspaceBasicResponse;
import com.accesshub.access_hub.responses.WorkspaceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final UserRepositoty userRepositoty;
    private final WorkspaceUserService workspaceUserService;

    public WorkspaceService(WorkspaceRepository workspaceRepository, UserRepositoty userRepositoty, WorkspaceUserService workspaceUserService) {
        this.workspaceRepository = workspaceRepository;
        this.userRepositoty = userRepositoty;
        this.workspaceUserService = workspaceUserService;
    }

    @Transactional(readOnly = true)
    public Page<WorkspaceResponse> myWorkspaces (User user, Pageable pageable) {
        return  workspaceRepository.findByCreator_idUser(user.getIdUser(), pageable).map(WorkspaceResponse::fromEntity);
    }

    @Transactional
    public Workspace createWorkspace(Workspace workspace, User user) {
        User us = userRepositoty.findById(user.getIdUser()).orElseThrow(() -> new RuntimeException("USER NÂO ENCONTRADO"));

        workspace.setCreator(us);
        workspace.setCreatedAt(LocalDateTime.now());

        Workspace saved = workspaceRepository.save(workspace);

        workspaceUserService.createWorkSpaceUser(user, saved, WorkspaceRoles.ADM);

        return  saved;
    }

    @Transactional
    public WorkspaceBasicResponse updateWorkSpace(WorkspaceUpdateDto dto, User user, UUID idWorkspace) {
        User us = userRepositoty.findById(user.getIdUser()).orElseThrow(() -> new RuntimeException("USER NÂO ENCONTRADO"));
        Workspace wk = workspaceRepository.findById(idWorkspace).orElseThrow(() -> new RuntimeException("WORKSPACE NÂO ENCONTRADO"));

        boolean canEdit =
                us.getIdUser().equals(wk.getCreator().getIdUser()) || us.isGlobalAdmin();


        if(!canEdit) throw new RuntimeException("VOCÊ NÂO POSSUI ESSAS PERMISÕES. VERIFIQUE COM ADMIN OU O DONO DO WORKSPACE");

        if(!dto.name().isBlank()) {
            wk.setName(dto.name());
        }

        wk.setUpdatedAt(LocalDateTime.now());

        return WorkspaceBasicResponse.toEntity(workspaceRepository.save(wk));

    }




}
