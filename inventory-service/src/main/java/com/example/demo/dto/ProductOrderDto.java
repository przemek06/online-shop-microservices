package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductOrderDto {
    Long id;
    @Email
    String ownerEmail;
    @NotNull
    @Size(max = 64)
    String address;
    @NotEmpty
    @NotNull
    List<ProductTicketDto> tickets;
    @NotNull
    String owner;
}
