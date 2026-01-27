package com.accesshub.access_hub.config.cookies;


import com.accesshub.access_hub.config.token.TokenService;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CookiesService {
    public static   final  String  COOKIE_NAME_KEY = "token_access";

    public ResponseCookie generateCookie(String jwtToken) {
        return  ResponseCookie.from(COOKIE_NAME_KEY, jwtToken)
                .secure(false)
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofHours(TokenService.JWT_EXPIRES_HOURS))
                .build();
    }
}
