package com.example.demo.Service;

import com.example.demo.Advice.Exception.DuplicateResourceException;
import com.example.demo.Advice.Exception.ResourceNotFoundException;
import com.example.demo.DTO.Mapper.UserMapper;
import com.example.demo.DTO.UpdateUserDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Extra.HelperFunctions;
import com.example.demo.Model.User;
import com.example.demo.Model.UserProfile;
import com.example.demo.Repository.UserDAO;
import com.example.demo.Repository.UserProfileDAO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final UserProfileDAO userProfileDAO;

    @Autowired
    public UserService(UserDAO userDAO, UserProfileDAO userProfileDAO) {
        this.userDAO = userDAO;
        this.userProfileDAO = userProfileDAO;
    }

    @Cacheable(value = "users", key = "#username")
    public UserDTO getUser(String username) {
        Optional<User> user = userDAO.findByUsername(username);

        if (user.isPresent())
            return UserMapper.toDTO(user.get());

        throw new ResourceNotFoundException("Username", username);
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if (userDAO.findByUsername(userDTO.getUsername()).isPresent())
            throw new DuplicateResourceException("Username", userDTO.getUsername());

        if (userProfileDAO.findByEmail(userDTO.getEmail()).isPresent())
            throw new DuplicateResourceException("Email", userDTO.getEmail());

        User user = UserMapper.getUser(userDTO);
        user.setPassword(HelperFunctions.getHashString(userDTO.getPassword()));
        user = userDAO.save(user);

        UserProfile userProfile = UserMapper.getUserProfile(userDTO);
        userProfile.setUser(user);
        userProfile = userProfileDAO.save(userProfile);

        user.setUserprofile(userProfile);

        return UserMapper.toDTO(user);
    }


    public User getUserByUsername(String username) {
        Optional<User> user = userDAO.findByUsername(username);
        if (user.isPresent())
            return user.get();

        throw new ResourceNotFoundException("Username", username);

    }

    @Transactional
    public UserDTO updateUser(String jwtUser, UpdateUserDTO userUpdate) {
        User user = userDAO.findByUsername(jwtUser).get();
        UserProfile userProfile = user.getUserprofile();


        if (userUpdate.getEmail() != null && !userUpdate.getEmail().equals(userProfile.getEmail())) {
            if (userProfileDAO.findByEmail(userUpdate.getEmail()).isPresent()) {
                throw new DuplicateResourceException("Email", userUpdate.getEmail());
            }

            String oldEmail = userProfile.getEmail();
            RequestContextHolder.currentRequestAttributes().setAttribute("oldEmail", oldEmail, RequestAttributes.SCOPE_REQUEST);

            userProfile.setEmail(userUpdate.getEmail());
        }

        if (userUpdate.getEmailNotification() != null)
            userProfile.setEmailNotification(userUpdate.getEmailNotification());

        if (userUpdate.getWeatherApiKey() != null)
            userProfile.setWeatherApiKey(userUpdate.getWeatherApiKey());

        return UserMapper.toDTO(user);
    }
}

