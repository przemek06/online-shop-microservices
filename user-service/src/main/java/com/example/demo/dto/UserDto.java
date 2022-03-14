package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotNull
    @Size(min = 4, max = 32)
    String username;
    @NotNull
    @Size(min = 4, max = 32)
    String password;
    String role;
    @NotNull
    @Email
    @Size(max = 32)
    String email;
}
