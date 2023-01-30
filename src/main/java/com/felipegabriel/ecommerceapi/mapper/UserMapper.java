package com.felipegabriel.ecommerceapi.mapper;

import com.felipegabriel.ecommerceapi.dto.UserDTO;
import com.felipegabriel.ecommerceapi.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDTO);

    UserDTO toDto(User user);
}
