package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    @NotNull
    @Size(min = 2, max = 6)
    private String code;
    @NotNull
    @Size(min = 2, max = 64)
    private String name;
    @NotNull
    private Integer amountAvailable;
}
