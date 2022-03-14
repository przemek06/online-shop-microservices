package com.example.demo.client;

import com.example.demo.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("user-service")
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET,
            value = "/api/users/{username}")
    UserDto getUser(@PathVariable String username);
}
