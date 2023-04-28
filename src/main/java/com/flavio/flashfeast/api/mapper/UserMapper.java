package com.flavio.flashfeast.api.mapper;

import com.flavio.flashfeast.api.model.input.UserInput;
import com.flavio.flashfeast.api.model.UserModel;
import com.flavio.flashfeast.domain.entities.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public User toEntity(UserInput userInput) {
        return modelMapper.map(userInput, User.class);
    }

    public UserModel toModel(User user) {
        return modelMapper.map(user, UserModel.class);
    }

    public List<UserModel> toCollectionModel(List<User> users) {
        return users.stream().map(this::toModel).collect(Collectors.toList());
    }
}
