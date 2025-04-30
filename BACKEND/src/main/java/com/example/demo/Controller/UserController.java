package com.example.demo.Controller;

import com.example.demo.DTO.UpdateUserDTO;
import com.example.demo.DTO.UserCacheDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Authorization")
public class UserController {

    private final UserService userService;
    private final CacheManager cacheManager;

    @Autowired
    public UserController(UserService userService, CacheManager cacheManager) {
        this.userService = userService;
        this.cacheManager = cacheManager;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser(
            @RequestParam(required = false) String username, @RequestParam(required = false, defaultValue = "false") boolean cacheData,
            @RequestAttribute("username") String jwtUser) {

        String finalUser = jwtUser;
        if (username != null && !username.isBlank())
            finalUser = username;

        long start = 0;
        boolean hit = false;

        if (cacheData) {
            start = System.currentTimeMillis();
            hit = cacheManager.getCache("users").get(finalUser) != null;
        }

        UserDTO userDTO = userService.getUser(finalUser);

        if (cacheData) {
            long finish = System.currentTimeMillis() - start;
            UserCacheDTO userCacheDTO = new UserCacheDTO(userDTO, finish, hit);

            return ResponseEntity.ok(userCacheDTO);
        }

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PatchMapping
    public ResponseEntity<UserDTO> updateUser(@RequestAttribute("username") String jwtUser, @RequestBody @Valid UpdateUserDTO dto) {
        return ResponseEntity.ok(userService.updateUser(jwtUser, dto));
    }

}
