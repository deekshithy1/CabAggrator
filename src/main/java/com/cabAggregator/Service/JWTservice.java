package com.cabAggregator.Service;

import com.cabAggregator.DTO.UserDetailsDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTservice {

    // Use a secure, long secret key (at least 32 chars for HS256)
    private final String SECRET_KEY = "THIS_IS_A_VERY_SECURE_SECRET_KEY_AT_LEAST_32_CHARS";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // Generate JWT token for a user
    public String GenerateToken(CustomUsers user) {
        Map<String, Object> claims = new HashMap<>();
        // Add custom claims if needed, for example user roles
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("id",user.getId());
        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        long now = System.currentTimeMillis();
        long expirationTime = 1000 * 60 * 60; // 1 hour in milliseconds

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public UserDetailsDTO extractUserDetails(String token) {
        Claims claims = extractClaims(token);

//        UserDetailsDTO userDetails = new UserDetailsDTO();
//        userDetails.id(Long.parseLong(claims.get("id").toString()));
//        userDetails.name(claims.get("username").toString());
//        userDetails.email(claims.get("email").toString());
//        userDetails.mobileNumber(claims.get("mobileNumber").toString());


        return new UserDetailsDTO((String) claims.get("id"), (String) claims.get("email"), (String) claims.get("name"), (String) claims.get("mobileNumber"));
    }
    public Claims extractClaims(String token) {
        return Jwts.parser()         // use parserBuilder() instead of deprecated parser()
                .setSigningKey(getSigningKey()) // provide the secret key
                .build()
                .parseClaimsJws(token)         // parse and validate signature
                .getBody();                    // return claims
    }

}
