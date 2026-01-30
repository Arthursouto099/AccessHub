package com.accesshub.access_hub.entities;
import com.accesshub.access_hub.enums.Roles;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Entity
@Table(name = "tb_user")
public class User implements UserDetails {

    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idUser;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private  String password;

    @Column()
    private  String profileImage;

    @ManyToMany()
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private  Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<WorkspaceUser> workspaceUsers = new ArrayList<>();


    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Workspace> workspaces = new ArrayList<>();

    public User(String username, String email, String password, String profileImage, Set<Role> roles, List<WorkspaceUser> workspaceUsers, List<Workspace> workspaces) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.roles = roles;
        this.workspaceUsers = workspaceUsers;
        this.workspaces = workspaces;
    }

    public User() {}

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  this.roles.stream().map((r) -> new SimpleGrantedAuthority("ROLE_" + r.getName().name())).toList();
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<String> getRolesAtStringList () {
        return this.roles.stream().map(r -> r.getName().name()).toList();
    }

    public List<Workspace> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    public List<WorkspaceUser> getWorkspaceUsers() {
        return workspaceUsers;
    }

    public void setWorkspaceUsers(List<WorkspaceUser> workspaceUsers) {
        this.workspaceUsers = workspaceUsers;
    }

    public  boolean isGlobalAdmin() {
        return  this.roles.stream().anyMatch(r -> r.getName() == Roles.ADMIN);
    }
}
