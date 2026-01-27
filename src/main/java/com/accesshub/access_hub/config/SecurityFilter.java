package com.accesshub.access_hub.config;

import com.accesshub.access_hub.config.cookies.CookiesService;
import com.accesshub.access_hub.config.token.JwtUserData;
import com.accesshub.access_hub.config.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private TokenService tokenService;
    private UserDetailsService userDetailsService;

    public  SecurityFilter(TokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromCookieHttpOnly(request);

        if(token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<JwtUserData> jwtUserData = tokenService.validatejwtToken(token);

            if(jwtUserData.isPresent()) {
                JwtUserData userData = jwtUserData.get();

                UserDetails userDetails = userDetailsService.loadUserByUsername(userData.email());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookieHttpOnly(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if(cookies == null) return null;

        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(CookiesService.COOKIE_NAME_KEY)) {
                var value = cookie.getValue();
                return  (value == null || value.isBlank()) ? null : value;
            }
        }

        return  null;
    }
}
