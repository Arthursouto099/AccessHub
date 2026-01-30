package com.accesshub.access_hub.controllers;

import com.accesshub.access_hub.config.cookies.CookiesService;
import com.accesshub.access_hub.dtos.UserCreateDto;
import com.accesshub.access_hub.dtos.UserLoginDto;
import com.accesshub.access_hub.responses.UserDefaultResponse;
import com.accesshub.access_hub.entities.User;
import com.accesshub.access_hub.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.accesshub.access_hub.config.token.TokenService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;


@RestController
public class AuthController {
    AuthService authService;
    AuthenticationManager authenticationManager;
    CookiesService cookiesService;
    TokenService tokenService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, CookiesService cookiesService, TokenService tokenService ) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.cookiesService = cookiesService;
        this.tokenService = tokenService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserDefaultResponse> registerUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        User user = authService.registerUser(UserCreateDto.toEntity(userCreateDto));
        return  ResponseEntity.status(201).body(UserDefaultResponse.fromEntity(user));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(Map.of(
                "uid", user.getIdUser(),
                "email", user.getEmail(),
                "roles", user.getAuthorities()
        ));
    }


    @PostMapping("/login")
    public  ResponseEntity<UserDefaultResponse> login(@RequestBody @Valid UserLoginDto userLoginDto, HttpServletResponse response) {


        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(userLoginDto.email(), userLoginDto.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        User user1 =  (User) authentication.getPrincipal();

        String token = tokenService.generateToken(user1);

        ResponseCookie cookie = cookiesService.generateCookie(token);

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return  ResponseEntity
                .status(HttpStatus.OK).body(UserDefaultResponse.fromEntity(user1));
    }

}
