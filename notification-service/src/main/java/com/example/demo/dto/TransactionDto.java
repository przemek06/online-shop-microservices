package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class TransactionDto {
    Long id;
    String address;
    String ownerEmail;
    @NotEmpty
    @NotNull
    List<OrderDto> orders;
}
