package com.felipegabriel.ecommerceapi.security;

import com.felipegabriel.ecommerceapi.dto.UserAuthenticationDTO;
import com.felipegabriel.ecommerceapi.dto.UserRegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        authenticationService.register(userRegistrationDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody UserAuthenticationDTO userAuthenticationDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(userAuthenticationDTO));
    }


}
