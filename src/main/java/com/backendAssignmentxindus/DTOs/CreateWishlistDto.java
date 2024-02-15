package com.backendAssignmentxindus.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CreateWishlistDto {
    private String name;

    private String description;

    private BigDecimal price;

    private String email;

    private String password;
}
