package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.mapper.UserLocationMapper;
import com.flavio.flashfeast.api.model.UserLocationModel;
import com.flavio.flashfeast.api.model.input.UserLocationInput;
import com.flavio.flashfeast.domain.entities.UserLocation;
import com.flavio.flashfeast.domain.service.UserLocationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-location")
public class UserLocationController {

    private final UserLocationService userLocationService;
    private final UserLocationMapper userLocationMapper;

    public UserLocationController(UserLocationService userLocationService, UserLocationMapper userLocationMapper) {
        this.userLocationService = userLocationService;
        this.userLocationMapper = userLocationMapper;
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<UserLocationModel> getUserLocation(@PathVariable int idUser) {
        UserLocation userLocation = userLocationService.getUserLocation(idUser);

        if(userLocation == null) return ResponseEntity.notFound().build();

        UserLocationModel userLocationModel = userLocationMapper.toModel(userLocation);
        return ResponseEntity.ok(userLocationModel);
    }

    @PostMapping("/{idUser}")
    public ResponseEntity<UserLocationModel> createUserLocation(@PathVariable int idUser,
                                                                @Valid @RequestBody UserLocationInput userLocationInput) {
        UserLocation userLocation = userLocationMapper.toEntity(userLocationInput);
        UserLocation userLocationResponse = userLocationService.createUserLocation(idUser, userLocation);

        if(userLocationResponse == null) return ResponseEntity.badRequest().build();

        UserLocationModel userLocationModel = userLocationMapper.toModel(userLocationResponse);
        return ResponseEntity.ok(userLocationModel);
    }

    @PutMapping ("/{idUser}")
    public ResponseEntity<UserLocationModel> updateUserLocation(@PathVariable int idUser,
                                                                @Valid @RequestBody UserLocationInput userLocationInput) {
        UserLocation userLocation = userLocationMapper.toEntity(userLocationInput);
        UserLocation userLocationResponse = userLocationService.updateUserLocation(idUser, userLocation);

        if(userLocationResponse == null) return ResponseEntity.badRequest().build();

        UserLocationModel userLocationModel = userLocationMapper.toModel(userLocationResponse);
        return ResponseEntity.ok(userLocationModel);
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity<?> deleteUserLocation(@PathVariable int idUser) {
        boolean successfullyDeleted = userLocationService.deleteUserLocation(idUser);

        if(!successfullyDeleted) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }
}
