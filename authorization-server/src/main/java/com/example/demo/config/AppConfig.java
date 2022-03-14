package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class AppConfig {

    @Autowired
    WebSecurityConfigurerAdapter securityConfigurer;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return securityConfigurer.authenticationManagerBean();
    }
}
