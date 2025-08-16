
package com.alura.forumhub.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alura.forumhub.model.Usuario;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-hours:2}")
    private Long expirationHours;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var expiresAt = LocalDateTime.now().plusHours(expirationHours)
                    .toInstant(ZoneOffset.of("-03:00"));
            return JWT.create()
                    .withIssuer("forumhub-api")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT.", exception);
        }
    }

    public String getSubject(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("forumhub-api")
                .build()
                .verify(token)
                .getSubject();
    }
}
