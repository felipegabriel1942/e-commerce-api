package com.felipegabriel.ecommerceapi.security;

import com.felipegabriel.ecommerceapi.dto.UserDTO;
import com.felipegabriel.ecommerceapi.enums.Role;
import com.felipegabriel.ecommerceapi.model.entity.User;
import com.felipegabriel.ecommerceapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public void register(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    public String authenticate(UserDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                )
        );

        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow();

        return jwtService.generateToken(user);
    }
}
