package com.accesshub.access_hub.config.token;


import java.util.List;
import java.util.UUID;

public record JwtUserData(UUID userId, String email, List<String> roles) { }
