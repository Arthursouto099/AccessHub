package com.accesshub.access_hub.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_workspace")
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  UUID idWorkspace;

    @Column(nullable = false)
    private  String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
    private List<WorkspaceUser> workspaceUsers = new ArrayList<>();

    @Column()
    private LocalDateTime createdAt;

    @Column()
    private  LocalDateTime updatedAt;

    public Workspace() {};

    public Workspace(String name, User creator, List<WorkspaceUser> workspaceUsers, LocalDateTime createdAt) {
        this.name = name;
        this.creator = creator;
        this.workspaceUsers = workspaceUsers;
        this.createdAt = createdAt;
    }

    public UUID getIdWorkspace() {
        return idWorkspace;
    }

    public void setIdWorkspace(UUID idWorkspace) {
        this.idWorkspace = idWorkspace;
    }

    public List<WorkspaceUser> getWorkspaceUsers() {
        return workspaceUsers;
    }

    public void setWorkspaceUsers(List<WorkspaceUser> workspaceUsers) {
        this.workspaceUsers = workspaceUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreator() {
        return creator;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
