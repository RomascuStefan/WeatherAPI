package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class UserProfile {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false)
    @Size(max = 255)
    private String email;

    @Column(name = "email_notification", nullable = false)
    private Boolean emailNotification;

    @Column(name = "weather_api_key")
    @Size(max = 255)
    private String weatherApiKey;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;
}
