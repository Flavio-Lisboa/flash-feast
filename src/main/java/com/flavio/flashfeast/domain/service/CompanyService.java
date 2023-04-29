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

    public Company updateCompany(int id, Company company) {
        return companyRepository.findById(id).map(record -> {
            record.setCnpj(company.getCnpj());
            record.setName(company.getName());
            record.setEmail(company.getEmail());
            record.setPassword(company.getPassword());
            record.setPhone(company.getPhone());
            return companyRepository.save(record);
        }).orElse(null);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public boolean deleteCompany(int id) {
        boolean companyExists = companyRepository.existsById(id);

        if(!companyExists) return false;

        companyRepository.deleteById(id);
        return true;
    }
}
