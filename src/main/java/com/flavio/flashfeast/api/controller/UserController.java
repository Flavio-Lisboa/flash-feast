package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.mapper.UserMapper;
import com.flavio.flashfeast.api.model.AuthenticationResponseModel;
import com.flavio.flashfeast.api.model.input.LoginInput;
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

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponseModel> authenticate(@RequestBody LoginInput loginInput) {
        AuthenticationResponseModel authResponse = userService.authenticate(loginInput);
        return ResponseEntity.ok(authResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable int id, @Valid @RequestBody UserInput userInput) {
        User user = userMapper.toEntity(userInput);
        User userResponse = userService.updateUser(id, user);

        UserModel userModel = userMapper.toModel(userResponse);
        return ResponseEntity.ok(userModel);
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> findAllUsers() {
        List<User> users = userService.findAll();
        if(users.isEmpty()) return ResponseEntity.noContent().build();

        List<UserModel> usersModel = userMapper.toCollectionModel(users);
        return ResponseEntity.ok(usersModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
