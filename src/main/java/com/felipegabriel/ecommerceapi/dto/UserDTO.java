package com.felipegabriel.ecommerceapi.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String email;

    private String password;
}
