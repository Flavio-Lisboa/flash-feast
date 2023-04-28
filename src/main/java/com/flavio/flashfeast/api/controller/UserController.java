package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.mapper.UserMapper;
import com.flavio.flashfeast.api.model.input.UserInput;
import com.flavio.flashfeast.api.model.UserModel;
import com.flavio.flashfeast.domain.entities.User;
import com.flavio.flashfeast.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody UserInput userInput) {
        User user = userMapper.toEntity(userInput);
        UserModel userResponse = userMapper.toModel(userService.createUser(user));
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userMapper.toCollectionModel(userService.findAll());
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body("deleted user");
    }
}
