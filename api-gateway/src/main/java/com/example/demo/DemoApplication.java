package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Przemek", "User");
//        securityContext.setAuthentication(token);
//        SecurityContextHolder.setContext(securityContext);
//
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
