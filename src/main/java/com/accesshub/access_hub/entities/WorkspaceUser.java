package com.accesshub.access_hub.entities;


import com.accesshub.access_hub.enums.WorkspaceRoles;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_workspaceUser")
public class WorkspaceUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  UUID idWorkspaceUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idWorkspace", nullable = false)
    private  Workspace workspace;

    @Column()
    private LocalDateTime joinedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkspaceRoles role;

    public WorkspaceUser(User user, LocalDateTime joinedAt, Workspace workspace, WorkspaceRoles role) {
        this.user = user;
        this.joinedAt = joinedAt;
        this.workspace = workspace;
        this.role = role;
    }

    public  WorkspaceUser() {}

    public UUID getIdWorkspaceUser() {
        return idWorkspaceUser;
    }

    public void setIdWorkspaceUser(UUID idWorkspaceUser) {
        this.idWorkspaceUser = idWorkspaceUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public WorkspaceRoles getRole() {
        return role;
    }

    public void setRole(WorkspaceRoles role) {
        this.role = role;
    }
}
