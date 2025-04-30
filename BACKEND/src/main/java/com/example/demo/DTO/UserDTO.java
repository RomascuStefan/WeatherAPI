package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "username obligatoriu")
    private String username;

    @NotBlank(message = "nume obligatoriu")
    private String nume;

    @NotBlank(message = "email obligatoriu")
    @Email(message = "email nu este valid")
    private String email;

    private Boolean emailNotification = false;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String weatherApiKey;
}
