package com.example.demo.Service;

import com.example.demo.DTO.Mapper.UserMapper;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Extra.HelperFunctions;
import com.example.demo.Model.User;
import com.example.demo.Model.UserProfile;
import com.example.demo.Repository.UserDAO;
import com.example.demo.Repository.UserProfileDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<UserDTO> getUser(String username) {
        Optional<User> user = userDAO.findByUsername(username);

        if (user.isPresent())
            return ResponseEntity.ok(UserMapper.toDTO(user.get()));

        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
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
        if(user.isPresent())
            return user.get();

        return null;
    }

}

