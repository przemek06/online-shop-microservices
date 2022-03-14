package com.example.demo.utils;

import com.example.demo.model.AppUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;


import java.util.Date;
import java.util.function.Function;

public class JWTUtil {
    private static final String SECRET_KEY = "secret";

    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public static AppUserDetails parseToken(String token) {
        try {
            if (isTokenExpired(token)) return null;

            Claims body = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return AppUserDetails.builder().username(body.getSubject())
                    .role((String) body.get("role"))
                    .email((String) body.get("email")).build();

        } catch (JwtException | ClassCastException | NullPointerException e) {
            return null;
        }
    }

}