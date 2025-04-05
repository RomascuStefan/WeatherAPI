package com.example.demo.DTO.Mapper;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Model.User;
import com.example.demo.Model.UserProfile;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setNume(user.getNume());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getUserprofile().getEmail());
        userDTO.setEmailNotification(user.getUserprofile().getEmailNotification());
        return userDTO;
    }

    public static User getUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setNume(userDTO.getNume());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public static UserProfile getUserProfile(UserDTO userDTO) {
        UserProfile profile = new UserProfile();
        profile.setEmail(userDTO.getEmail());
        profile.setEmailNotification(userDTO.getEmailNotification());
        profile.setWeatherApiKey(userDTO.getWeatherApiKey());
        return profile;
    }
}
