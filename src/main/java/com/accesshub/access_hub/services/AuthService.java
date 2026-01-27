package com.accesshub.access_hub.services;

import com.accesshub.access_hub.entities.Role;
import com.accesshub.access_hub.entities.User;
import com.accesshub.access_hub.enums.Roles;
import com.accesshub.access_hub.repositories.RoleRepository;
import com.accesshub.access_hub.repositories.UserRepositoty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthService  {
    private  PasswordEncoder passwordEncoder;
    private UserRepositoty userRepositoty;
    private RoleRepository roleRepository;

    public AuthService(PasswordEncoder passwordEncoder, UserRepositoty userRepositoty, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepositoty = userRepositoty;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public User registerUser(User user) {
        Role role = roleRepository.findByName(Roles.BASIC).orElseThrow(() -> new RuntimeException("Role não encontrada."));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(role);
        return  userRepositoty.save(user);
    }

    private Boolean confirmPassword(String password, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public  User login(String email, String password)  {
        User user = userRepositoty.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        Boolean isPasswordValid = confirmPassword(password, user);

        if(!isPasswordValid) throw new RuntimeException("Senha incorreta.");

        return  user;
    }
}
