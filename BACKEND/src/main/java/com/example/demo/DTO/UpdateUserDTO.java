package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateUserDTO {

    @Email
    private String email;

    private Boolean emailNotification;

    private String weatherApiKey;
}
