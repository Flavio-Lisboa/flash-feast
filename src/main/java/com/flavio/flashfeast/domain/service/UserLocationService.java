package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.domain.entities.UserLocation;
import com.flavio.flashfeast.domain.repository.UserLocationRepository;
import com.flavio.flashfeast.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserLocationService {

    private final UserLocationRepository userLocationRepository;
    private final UserRepository userRepository;

    public UserLocationService(UserLocationRepository userLocationRepository, UserRepository userRepository) {
        this.userLocationRepository = userLocationRepository;
        this.userRepository = userRepository;
    }

    public UserLocation getUserLocation(int idUser) {
        return userLocationRepository.findById(idUser).orElse(null);
    }

    public UserLocation createUserLocation(int idUser, UserLocation userLocation) {
        boolean exists = userLocationRepository.existsById(idUser);

        if(exists) return null;

        boolean user = userRepository.existsById(idUser);

        if(!user) return null;

        userLocation.setUserId(idUser);
        return userLocationRepository.save(userLocation);
    }

    public boolean deleteUserLocation(int idUser) {
        boolean exists = userLocationRepository.existsById(idUser);

        if(!exists) return false;

        userLocationRepository.deleteById(idUser);
        return true;
    }

    public UserLocation updateUserLocation(int idUser, UserLocation userLocation) {
        boolean exists = userLocationRepository.existsById(idUser);

        if(!exists) return null;

        userLocation.setUserId(idUser);
        return userLocationRepository.save(userLocation);
    }
}
