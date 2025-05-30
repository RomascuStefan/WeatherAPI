package com.example.demo.Service;

import com.example.demo.DTO.AuthDTO;
import com.example.demo.Advice.Exception.InvalidCredentialsException;
import com.example.demo.Extra.HelperFunctions;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserDAO;
import com.example.demo.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private final UserDAO userDAO;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserDAO userDAO,  JwtUtil jwtUtil) {
        this.userDAO = userDAO;
        this.jwtUtil = jwtUtil;
    }


    public String login(AuthDTO auth) {
        User user = userDAO.findByUsername(auth.getUsername()).orElseThrow(InvalidCredentialsException::new);

        if (!HelperFunctions.passwordsMatch(auth.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}
