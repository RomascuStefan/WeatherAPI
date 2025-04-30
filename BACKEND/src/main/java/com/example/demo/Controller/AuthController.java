package com.example.demo.Controller;

import com.example.demo.DTO.AuthDTO;
import com.example.demo.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Authorization")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthDTO authDTO) {
        String token = authService.login(authDTO);
        return ResponseEntity.ok(token);
    }
}