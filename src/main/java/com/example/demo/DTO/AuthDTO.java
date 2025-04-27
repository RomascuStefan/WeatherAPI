package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDTO {
    @NotBlank(message = "username obligatoriu")
    private String username;

    @NotBlank(message = "parola obligatorie")
    private String password;
}