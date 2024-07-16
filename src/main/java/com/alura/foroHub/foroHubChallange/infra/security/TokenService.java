package com.alura.foroHub.foroHubChallange.infra.security;


import com.alura.foroHub.foroHubChallange.domain.usuario.Usuario;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.springframework.security.config.Elements.JWT;

@Service
public class TokenService {
    @Value("${forohub.security.secret}")
    private String forohubSecret;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(forohubSecret);//Ver nota
            return com.auth0.jwt.JWT.create()
                    .withIssuer("forohub")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarTiempoDeExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException();
        }
    }
    public String getSubject(String token){
        if (token == null){
            throw new RuntimeException("El token no puede ser nulo.");
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(forohubSecret);//Validando firma
            verifier = com.auth0.jwt.JWT.require(algorithm)
                    .withIssuer("forohub")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception){
            System.out.println(exception.toString());
        }
        if(verifier.getSubject() == null){
            throw new RuntimeException("Verifier es inválido.");
        }
        return verifier.getSubject();
    }

    private Instant generarTiempoDeExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}