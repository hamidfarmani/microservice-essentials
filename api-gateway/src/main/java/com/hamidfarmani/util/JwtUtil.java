package com.hamidfarmani.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private String secret;


    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
    public void validateToken(final String token) {
        Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
    }

}
