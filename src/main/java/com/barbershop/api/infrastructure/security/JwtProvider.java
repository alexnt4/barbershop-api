package com.barbershop.api.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Proveedor de JWT para generar y validar tokens.
 */
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Genera la clave secreta a partir del secreto configurado.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un token JWT para el usuario dado.
     *
     * @param dni DNI del usuario
     * @param email email del usuario
     * @param role rol del usuario
     * @return token JWT
     */
    public String generateToken(final String dni, final String email, final String role) {
        final Date now = new Date();
        final Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(dni)
                .claim("email", email)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Obtiene el DNI del usuario desde el token.
     *
     * @param token token JWT
     * @return DNI del usuario
     */
    public String getDniFromToken(final String token) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    /**
     * Obtiene el rol del usuario desde el token.
     *
     * @param token token JWT
     * @return rol del usuario
     */
    public String getRoleFromToken(final String token) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("role", String.class);
    }

    /**
     * Valida si el token es válido (no expirado y firma correcta).
     *
     * @param token token JWT
     * @return true si es válido
     */
    public boolean validateToken(final String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }
}
