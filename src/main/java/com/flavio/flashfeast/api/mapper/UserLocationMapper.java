package com.flavio.flashfeast.api.mapper;

import com.flavio.flashfeast.api.model.UserLocationModel;
import com.flavio.flashfeast.api.model.input.UserLocationInput;
import com.flavio.flashfeast.domain.entities.UserLocation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserLocationMapper {

    private final ModelMapper modelMapper;

    public UserLocationModel toModel(UserLocation userLocation) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userLocation, UserLocationModel.class);
    }

    public UserLocation toEntity(UserLocationInput userLocationInput) {
        return modelMapper.map(userLocationInput, UserLocation.class);
    }
}
