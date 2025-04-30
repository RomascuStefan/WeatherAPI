package com.example.demo.DTO;

import lombok.Getter;

@Getter
public class UserCacheDTO extends UserDTO {
    private long timeMs;
    private boolean cacheHit;

    public UserCacheDTO(UserDTO user, long timeMs, boolean cacheHit) {
        this.setUsername(user.getUsername());
        this.setNume(user.getNume());
        this.setEmail(user.getEmail());
        this.setEmailNotification(user.getEmailNotification());

        this.timeMs = timeMs;
        this.cacheHit   = cacheHit;
    }
}
