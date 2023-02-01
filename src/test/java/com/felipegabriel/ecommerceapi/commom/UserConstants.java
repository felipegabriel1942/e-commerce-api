package com.felipegabriel.ecommerceapi.commom;

import com.felipegabriel.ecommerceapi.dto.UserDTO;
import com.felipegabriel.ecommerceapi.enums.Role;
import com.felipegabriel.ecommerceapi.model.entity.User;

public class UserConstants {

    public static final User USER = User.builder().email("user@mail.com").role(Role.USER).build();
    public static final UserDTO USER_DTO = UserDTO.builder().email("user@mail.com").role(Role.USER).build();
    public static final User USER_ADMIN = User.builder().email("admin@mail.com").role(Role.ADMIN).build();
}
