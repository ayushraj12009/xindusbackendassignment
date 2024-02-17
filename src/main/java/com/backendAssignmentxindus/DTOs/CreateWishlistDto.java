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

    private Integer price;

    private String email;

    private String password;

    public CreateWishlistDto(String name, String description, int price, String email, String password) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.email = email;
        this.password = password;


    }
}
