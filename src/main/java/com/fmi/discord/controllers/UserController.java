package com.fmi.discord.controllers;

import com.fmi.discord.entities.User;
import com.fmi.discord.http.AppResponse;
import com.fmi.discord.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (this.userService.createUser(user)) {
            return AppResponse.success().withMessage("User created successfully").build();
        }

        return AppResponse.error()
                .withMessage("User could not be created")
                .build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User userResult = this.userService.getUserById(id);

        if (userResult == null) {
            return AppResponse.error().withMessage("User data not found").build();
        }

        return AppResponse.success().withData(userResult).build();
    }
}
