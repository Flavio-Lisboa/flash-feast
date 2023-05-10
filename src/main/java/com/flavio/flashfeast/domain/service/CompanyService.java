package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.domain.entities.Company;
import com.flavio.flashfeast.domain.enums.Role;
import com.flavio.flashfeast.domain.exception.DomainException;
import com.flavio.flashfeast.domain.repository.CompanyRepository;
import com.flavio.flashfeast.domain.utils.CloudinaryUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CloudinaryUtil cloudinaryUtil;

    public CompanyService(CompanyRepository companyRepository, CloudinaryUtil cloudinaryUtil) {
        this.companyRepository = companyRepository;
        this.cloudinaryUtil = cloudinaryUtil;
    }

    public Company createCompany(Company company, MultipartFile logo) {
        emailExists(company);

        String folder = "company_logo";
        String logoUrl = cloudinaryUtil.uploadFile(logo, folder);
        Company companyBuilder = Company.builder()
                .cnpj(company.getCnpj())
                .name(company.getName())
                .email(company.getEmail())
                .password(company.getPassword())
                .phone(company.getPhone())
                .role(Role.ROLE_COMPANY)
                .logo(logoUrl)
                .build();
        return companyRepository.save(companyBuilder);
    }

    public Company updateCompany(int id, Company company) {
        company.setId(id);
        emailExists(company);

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

    public void emailExists(Company company) {
        boolean emailExists = companyRepository.findByEmail(company.getEmail()).stream().anyMatch(existingCompany-> !existingCompany.equals(company));
        if(emailExists) throw new DomainException("there is already a registered company with this email");
    }
}
