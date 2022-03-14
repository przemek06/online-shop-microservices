package com.example.demo.utils;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import org.springframework.beans.BeanUtils;

public class UserUtils {
    public static UserDto entityToDto(UserEntity entity) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public static UserEntity dtoToEntity(UserDto dto) {
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
