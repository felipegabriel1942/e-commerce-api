package com.felipegabriel.ecommerceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuthenticationDTO {

    private String email;

    private String password;
}
