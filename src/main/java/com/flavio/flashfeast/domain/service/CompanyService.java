package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.domain.entities.Company;
import com.flavio.flashfeast.domain.enums.Role;
import com.flavio.flashfeast.domain.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company company) {
        Company companyBuilder = Company.builder()
                .cnpj(company.getCnpj())
                .name(company.getName())
                .email(company.getEmail())
                .password(company.getPassword())
                .phone(company.getPhone())
                .role(Role.ROLE_COMPANY)
                .logo("https://logo.com")
                .build();
        return companyRepository.save(companyBuilder);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}
