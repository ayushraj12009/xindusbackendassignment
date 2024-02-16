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

    public CreateWishlistDto(String itemName, String itemDescription, int i, String email, String testpassword) {

        this.name = itemName;
        this.description = itemDescription;
        this.price = i;
        this.email = email;
        this.password = testpassword;


    }
}
