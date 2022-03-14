package com.example.demo.model;

import org.springframework.security.core.userdetails.UserDetails;

public interface AppUserDetails extends UserDetails {
    String getEmail();
}
