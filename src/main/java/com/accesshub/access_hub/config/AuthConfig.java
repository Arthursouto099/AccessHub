package com.accesshub.access_hub.config;

import com.accesshub.access_hub.repositories.UserRepositoty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthConfig implements UserDetailsService {

    private UserRepositoty userRepositoty;

    public  AuthConfig(UserRepositoty userRepositoty) {
        this.userRepositoty = userRepositoty;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userRepositoty.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
