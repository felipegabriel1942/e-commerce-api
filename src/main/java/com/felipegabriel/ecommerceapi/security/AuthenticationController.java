package com.felipegabriel.ecommerceapi.security;

import com.felipegabriel.ecommerceapi.dto.UserAuthenticationDTO;
import com.felipegabriel.ecommerceapi.dto.UserRegistrationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(
            summary = "Register user",
            description = "Register new user. The same e-mail cannot be used twice to register."
    )
    public ResponseEntity<Void> register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        authenticationService.register(userRegistrationDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate")
    @Operation(
            summary = "Authenticate user",
            description = "Authenticates the user and returns an access token."
    )
    public ResponseEntity<String> authenticate(@RequestBody UserAuthenticationDTO userAuthenticationDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(userAuthenticationDTO));
    }


}
