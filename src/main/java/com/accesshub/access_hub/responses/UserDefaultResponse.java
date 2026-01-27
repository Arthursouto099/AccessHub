package com.accesshub.access_hub.responses;

import com.accesshub.access_hub.entities.Role;
import com.accesshub.access_hub.entities.User;

import java.util.List;
import java.util.Set;

public record UserDefaultResponse(
        String username,
        String email,
        String profileImage,
        List<String> roles
) {
    static  public  UserDefaultResponse  fromEntity(User user) {
        return  new UserDefaultResponse(
                user.getUsername(),
                user.getEmail(),
                user.getProfileImage(),
                user.getRoles() == null ? List.of() : user.getRolesAtStringList()
        );
    }
}
