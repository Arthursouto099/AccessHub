package com.accesshub.access_hub.services;

import com.accesshub.access_hub.entities.User;
import com.accesshub.access_hub.entities.Workspace;
import com.accesshub.access_hub.entities.WorkspaceUser;
import com.accesshub.access_hub.enums.WorkspaceRoles;
import com.accesshub.access_hub.repositories.WorkspaceUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class WorkspaceUserService {
    private  final  WorkspaceUserRepository workspaceUserRepository;

    public WorkspaceUserService(WorkspaceUserRepository workspaceUserRepository) {
        this.workspaceUserRepository = workspaceUserRepository;
    }

    @Transactional
    public WorkspaceUser createWorkSpaceUser(User user, Workspace workspace, WorkspaceRoles role) {
        WorkspaceUser workspaceUser = new WorkspaceUser();
        workspaceUser.setUser(user);
        workspaceUser.setJoinedAt(LocalDateTime.now());
        workspaceUser.setWorkspace(workspace);
        workspaceUser.setRole(role);
        return  workspaceUserRepository.save(workspaceUser);
    }
}
