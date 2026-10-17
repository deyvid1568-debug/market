package com.github.dev.deyvid.market.modules.auth.controller;

import com.github.dev.deyvid.market.modules.auth.dto.LoginDTO;
import com.github.dev.deyvid.market.modules.auth.dto.LoginResponseDTO;
import com.github.dev.deyvid.market.modules.auth.dto.RegisterDTO;
import com.github.dev.deyvid.market.modules.auth.model.User;
import com.github.dev.deyvid.market.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDTO data) {
        User newUser = authService.register(data.email(), data.password(), data.name());
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO data) {
        String token = authService.login(data.email(), data.password());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}