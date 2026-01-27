package com.accesshub.access_hub.config.token;

import com.accesshub.access_hub.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenService {

    private final Algorithm algorithm;
    private final long accessExpirationHOURS;
    public static final long JWT_EXPIRES_HOURS = 2;

    public TokenService(@Value("${jwt.secret}") String secretKey, @Value("${jwt.access.expiration}") long accessExpirationHOURS) {
        this.algorithm = Algorithm.HMAC256(secretKey);
        this.accessExpirationHOURS = accessExpirationHOURS;
    }

    public  String generateToken(User user) {
        return JWT.create().
                withSubject(user.getEmail())
                .withClaim("uid", user.getIdUser().toString())
                .withClaim("roles", user.getRolesAtStringList())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(accessExpirationHOURS)))
                .sign(algorithm);

    }

    public Optional<JwtUserData> validatejwtToken(String  jwtToken) {
        try {
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build().verify(jwtToken);

            return  Optional.of(new JwtUserData(
                    UUID.fromString(decodedJWT.getClaim("uid").asString()),
                    decodedJWT.getSubject(),
                    decodedJWT.getClaim("roles").asList(String.class)
            ));
        }

        catch (JWTVerificationException ex) {
            return  Optional.empty();
        }


    }
}
