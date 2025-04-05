package com.example.demo.Extra;

import com.example.demo.Model.UserProfile;
import com.example.demo.Repository.UserProfileDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserProfileDAO userProfileDAO;

    public DataLoader(UserProfileDAO userProfileDAO) {
        this.userProfileDAO = userProfileDAO;
    }

    @Override
    public void run(String... args) throws Exception {/*
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail("test@gmail.com");
        userProfile.setEmailNotification(true);
        userProfile.setWeatherApiKey("TEST");

        userProfileDAO.save(userProfile);*/
    }
}
