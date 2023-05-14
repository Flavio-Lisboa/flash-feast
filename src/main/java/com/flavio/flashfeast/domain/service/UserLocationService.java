package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.domain.entities.UserLocation;
import com.flavio.flashfeast.domain.exception.AlreadyExistsException;
import com.flavio.flashfeast.domain.exception.NotFoundException;
import com.flavio.flashfeast.domain.repository.UserLocationRepository;
import com.flavio.flashfeast.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
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
        boolean user = userRepository.existsById(idUser);
        if(!user) throw new NotFoundException("User Not Found");

        boolean exists = userLocationRepository.existsById(idUser);
        if(exists) throw new AlreadyExistsException("Location Already Exists");

        userLocation.setUserId(idUser);
        return userLocationRepository.save(userLocation);
    }

    public void deleteUserLocation(int idUser) {
        boolean exists = userLocationRepository.existsById(idUser);
        if(!exists) throw new NotFoundException("Location Not Found");

        userLocationRepository.deleteById(idUser);
    }

    public UserLocation updateUserLocation(int idUser, UserLocation userLocation) {
        boolean exists = userLocationRepository.existsById(idUser);
        if(!exists) throw new NotFoundException("Location Not Found");

        userLocation.setUserId(idUser);
        return userLocationRepository.save(userLocation);
    }
}
