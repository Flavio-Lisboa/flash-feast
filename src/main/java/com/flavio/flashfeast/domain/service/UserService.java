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

    public boolean deleteUser(int id) {
        boolean userExists = userRepository.existsById(id);

        if(!userExists) return false;

        userRepository.deleteById(id);
        return true;
    }

    public User updateUser(int id, User user) {
        return userRepository.findById(id).map(record -> {
            record.setFirstName(user.getFirstName());
            record.setLastName(user.getLastName());
            record.setEmail(user.getEmail());
            record.setPassword(user.getPassword());
            record.setPhone(user.getPhone());
            record.setCpf(user.getCpf());
            return userRepository.save(record);
        }).orElse(null);
    }
}
