package com.example.demo.service;

import com.example.demo.dto.AuthenticationRequestDto;
import com.example.demo.model.AppUserDetails;
import com.example.demo.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;

    public ResponseEntity<String> login(AuthenticationRequestDto request, HttpServletResponse response){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Bad credentials");
        }

        final AppUserDetails userDetails = (AppUserDetails) userDetailsService
                .loadUserByUsername(request.getUsername());

        final String jwt = JWTUtil.generateToken(userDetails);

        Cookie cookie = new Cookie("Auth", "Bearer_" + jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return ResponseEntity.ok().body("Authenticated!");
    }
}
