package com.example.demo.service;

import com.example.demo.client.UserClient;
import com.example.demo.dto.UserDto;
import com.example.demo.model.AppUserDetails;
import com.example.demo.model.AppUserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserClient userClient;

    public UserDetailsServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userClient.getUser(username);
        return new AppUserDetailsImpl(userDto);
    }
}
