package com.blog.blogapi.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthTokenGenerator {
    private static final long EXPIRATION= 30 * 60 * 1000l;
    private static final String SECRET_KEY="dahsg43654&^78s";

    public String generateAuthToken(String email){
        return Jwts.builder()
                .setIssuer("blogs-api")
                .setSubject(email)
                .setExpiration(new Date(EXPIRATION+System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }
}
