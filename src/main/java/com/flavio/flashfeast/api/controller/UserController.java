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
import java.util.stream.Collectors;

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
    public ResponseEntity<List<UserModel>> findAllUsers() {
        List<User> users = userService.findAll();
        if(users.isEmpty()) return ResponseEntity.noContent().build();

        List<UserModel> usersModel = userMapper.toCollectionModel(users);
        return ResponseEntity.ok(usersModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean userExists = userService.deleteUser(id);

        if(userExists) return ResponseEntity.ok().build();
        else return ResponseEntity.notFound().build();
    }
}
