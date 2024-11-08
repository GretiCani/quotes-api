package com.quotes.controller;

import com.quotes.domain.request.RegisterRequest;
import com.quotes.service.TokenService;
import com.quotes.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final UserService userService;

    @SecurityRequirement(name = "basicAuth")
    @PostMapping("/login")
    public String generateToken(Authentication auth){
        return tokenService.generateToken(auth);
    }


    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegisterRequest req){
        userService.createUser(req);
        return ResponseEntity.noContent().build();
    }
}