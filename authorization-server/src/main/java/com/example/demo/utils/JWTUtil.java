package com.example.demo.utils;

import com.example.demo.dto.UserDto;
import com.example.demo.model.AppUserDetails;
import com.example.demo.model.AppUserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    public static String generateToken(AppUserDetails u) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("email", u.getEmail());
        GrantedAuthority authority = (GrantedAuthority) u.getAuthorities().toArray()[0];
        claims.put("role", authority.getAuthority());

        return Jwts.builder()
                .setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60*6))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }


    public static UserDetails parseToken(String token) {
        try {
            if (isTokenExpired(token)) return null;

            Claims body = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();


            return new AppUserDetailsImpl(
                    UserDto.builder().username(body.getSubject())
                            .role((String) body.get("role"))
                            .email((String) body.get("email")).build());

        } catch (JwtException | ClassCastException | NullPointerException e) {
            return null;
        }
    }

}