package com.example.demo.Controller;

import com.example.demo.DTO.UpdateUserDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser(@RequestParam(required = false) String username, @RequestAttribute("username") String jwtUser) {
        if (username != null) return userService.getUser(username);

        return userService.getUser(jwtUser);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PatchMapping
    public UserDTO updateUser(@RequestAttribute("username") String jwtUser, @RequestBody @Valid UpdateUserDTO dto) {
        return userService.updateUser(jwtUser, dto);
    }

}
