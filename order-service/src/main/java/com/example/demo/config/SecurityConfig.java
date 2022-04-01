package com.example.demo.config;

import com.example.demo.filter.AuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    AuthenticationFilter authenticationFilter;

    public SecurityConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/api/orders/**").hasAuthority("ADMIN_ROLE")
                .antMatchers(HttpMethod.GET, "/api/orders/**").hasAuthority("ADMIN_ROLE")
                .antMatchers(HttpMethod.POST, "/api/orders/**").hasAuthority("USER_ROLE")
                .anyRequest().permitAll()
                .and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}