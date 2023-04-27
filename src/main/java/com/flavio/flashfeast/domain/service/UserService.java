package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.domain.entities.User;
import com.flavio.flashfeast.domain.enums.Role;
import com.flavio.flashfeast.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        User userBuilder = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .cpf(user.getCpf())
                .role(Role.ROLE_USER)
                .build();
        return userRepository.save(userBuilder);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
