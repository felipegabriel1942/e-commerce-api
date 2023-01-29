package com.felipegabriel.ecommerceapi.dto;

import com.felipegabriel.ecommerceapi.enums.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private Role role;
}
