package com.flavio.flashfeast.api.mapper;

import com.flavio.flashfeast.api.model.CompanyModel;
import com.flavio.flashfeast.api.model.input.CompanyInput;
import com.flavio.flashfeast.domain.entities.Company;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CompanyMapper {

    private final ModelMapper modelMapper;

    public Company toEntity(CompanyInput companyInput) {
        return modelMapper.map(companyInput, Company.class);
    }

    public CompanyModel toModel(Company company) {
        return modelMapper.map(company, CompanyModel.class);
    }

    public List<CompanyModel> toCollectionModel(List<Company> companies) {
        return companies.stream().map(this::toModel).collect(Collectors.toList());
    }
}
