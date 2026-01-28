package com.quipux.playlist.service.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {
    private static final String SECRET = "CDvY&48JAU%JfDw(QEbM5(%;DRv5i%e~G-tn0X-a8YS-XFYFkh"; // 256 bits
    private static final long EXPIRATION = 86400000; // 1 día


    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }


    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractCorreo(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}