package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    UserRepository userRepository;

    @PostConstruct
    public void init(){
        UserEntity admin = UserEntity.builder()
                .email("swiatprzemyslaw@gmail.com")
                .username("admin")
                .password("pass")
                .role("ADMIN_ROLE")
                .build();

        UserEntity user = UserEntity.builder()
                .email("swiatprzemyslaw@gmail.com")
                .username("user")
                .password("pass")
                .role("USER_ROLE")
                .build();

        userRepository.saveAll(Arrays.asList(user, admin));
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream().map(UserUtils::entityToDto).collect(Collectors.toList());
    }

    public UserDto getById(Long id){
        Optional<UserEntity> optional = userRepository.findById(id);
        if(optional.isEmpty()) return new UserDto();
        return UserUtils.entityToDto(optional.get());
    }

    public UserDto getByUsername(String username){
        Optional<UserEntity> optional = userRepository.findUserEntityByUsername(username);
        if(optional.isEmpty()) return new UserDto();
        return UserUtils.entityToDto(optional.get());
    }

    public Boolean saveUser(UserDto userDto){
        if(userRepository.existsByUsername(userDto.getUsername())) return false;
        userDto.setRole("USER_ROLE");
        userRepository.save(UserUtils.dtoToEntity(userDto));
        return true;
    }
}
