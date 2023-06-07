package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.api.model.AuthenticationResponseModel;
import com.flavio.flashfeast.api.model.input.LoginInput;
import com.flavio.flashfeast.domain.entities.User;
import com.flavio.flashfeast.domain.enums.Role;
import com.flavio.flashfeast.domain.exception.AlreadyExistsException;
import com.flavio.flashfeast.domain.exception.NotFoundException;
import com.flavio.flashfeast.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public User createUser(User user) {
        emailExists(user);

        User userBuilder = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .phone(user.getPhone())
                .cpf(user.getCpf())
                .role(Role.ROLE_USER)
                .build();
        return userRepository.save(userBuilder);
    }

    public AuthenticationResponseModel authenticate(LoginInput request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        String token = jwtService.generateToken(user);
        return AuthenticationResponseModel.builder()
                .token(token)
                .build();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteUser(int id) {
        boolean userExists = userRepository.existsById(id);

        if(!userExists) throw new NotFoundException("User Not Found");

        userRepository.deleteById(id);
    }

    public User updateUser(int id, User user) {
        user.setId(id);
        emailExists(user);

        return userRepository.findById(id).map(record -> {
            record.setFirstName(user.getFirstName());
            record.setLastName(user.getLastName());
            record.setEmail(user.getEmail());
            record.setPassword(user.getPassword());
            record.setPhone(user.getPhone());
            record.setCpf(user.getCpf());
            return userRepository.save(record);
        }).orElseThrow(() -> new NotFoundException("User Not Found"));
    }

    public void emailExists(User user) {
        boolean emailExists = userRepository.findByEmail(user.getEmail())
                .stream().anyMatch(existingUser -> !existingUser.equals(user));
        if(emailExists) throw new AlreadyExistsException("there is already a registered user with this email");
    }
}
