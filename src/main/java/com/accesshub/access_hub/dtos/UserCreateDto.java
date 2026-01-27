package com.accesshub.access_hub.dtos;

import com.accesshub.access_hub.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDto(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        @Email
        String email,
        String profileImage
) {
    static  public User toEntity(UserCreateDto userCreateDto) {
        User user = new User();
        user.setUsername(userCreateDto.username);
        user.setEmail(userCreateDto.email);
        user.setPassword(userCreateDto.password);
        if(userCreateDto.profileImage != null &&  !userCreateDto.profileImage.isBlank()) {
            user.setProfileImage(userCreateDto.profileImage);
        }
        return  user;
    }
}
