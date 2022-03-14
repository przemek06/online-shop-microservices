package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

@RestController
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/api/users/{username}")
    public UserDto getByUsername(@PathVariable String username){
        return userService.getByUsername(username);
    }

    @PostMapping("/api/users")
    public Boolean saveUser(@RequestBody UserDto userDto){
        return userService.saveUser(userDto);
    }

    @ExceptionHandler(ValidationException.class)
    public Boolean handleBadUserDto(ValidationException e){
        return false;
    }
}
