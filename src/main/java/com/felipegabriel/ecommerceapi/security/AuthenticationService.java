package com.felipegabriel.ecommerceapi.security;

import com.felipegabriel.ecommerceapi.dto.UserAuthenticationDTO;
import com.felipegabriel.ecommerceapi.dto.UserRegistrationDTO;
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

    public void register(UserRegistrationDTO userRegistrationDTO) {
        User user = User.builder()
                .email(userRegistrationDTO.getEmail())
                .password(passwordEncoder.encode(userRegistrationDTO.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    public String authenticate(UserAuthenticationDTO userAuthenticationDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthenticationDTO.getEmail(),
                        userAuthenticationDTO.getPassword()
                )
        );

        User user = userRepository.findByEmail(userAuthenticationDTO.getEmail())
                .orElseThrow();

        return jwtService.generateToken(user);
    }
}
