package com.flavio.flashfeast.api.mapper;

import com.flavio.flashfeast.api.model.CompanyLocationModel;
import com.flavio.flashfeast.api.model.input.CompanyLocationInput;
import com.flavio.flashfeast.domain.entities.Company;
import com.flavio.flashfeast.domain.entities.CompanyLocation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CompanyLocationMapper {

    private final ModelMapper modelMapper;

    public CompanyLocationModel toModel(CompanyLocation companyLocation) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(companyLocation, CompanyLocationModel.class);
    }

    public CompanyLocation toEntity(CompanyLocationInput companyLocationInput) {
        return modelMapper.map(companyLocationInput, CompanyLocation.class);
    }
}
