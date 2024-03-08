package com.example.cloudgatewayop.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;



@Component
public class JwtUtil {
    private static final String SECRET_KEY="c38fa49f1f8e88e74d0b520443bb68696d5f3f2c7d8be93eb9b5b1bb2748f165";

    public void validateToken(final String token){
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);

    }
    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
