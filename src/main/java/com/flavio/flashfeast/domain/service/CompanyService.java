package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.api.model.AuthenticationResponseModel;
import com.flavio.flashfeast.api.model.input.LoginInput;
import  com.flavio.flashfeast.domain.entities.Company;
import com.flavio.flashfeast.domain.enums.Role;
import com.flavio.flashfeast.domain.exception.AlreadyExistsException;
import com.flavio.flashfeast.domain.exception.NotFoundException;
import com.flavio.flashfeast.domain.repository.CompanyRepository;
import com.flavio.flashfeast.domain.utils.CloudinaryUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CloudinaryUtil cloudinaryUtil;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public CompanyService(CompanyRepository companyRepository, CloudinaryUtil cloudinaryUtil,
                          PasswordEncoder passwordEncoder, JwtService jwtService,
                          AuthenticationManager authenticationManager) {
        this.companyRepository = companyRepository;
        this.cloudinaryUtil = cloudinaryUtil;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Company createCompany(Company company, MultipartFile logo) {
        emailExists(company);

        String folder = "company_logo";
        String logoUrl = cloudinaryUtil.uploadFile(logo, folder);
        Company companyBuilder = Company.builder()
                .cnpj(company.getCnpj())
                .name(company.getName())
                .email(company.getEmail())
                .password(passwordEncoder.encode(company.getPassword()))
                .phone(company.getPhone())
                .role(Role.ROLE_COMPANY)
                .logo(logoUrl)
                .build();
        return companyRepository.save(companyBuilder);
    }

    public AuthenticationResponseModel authenticate(LoginInput request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Company company = companyRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("Company Not Found"));

        String token = jwtService.generateToken(company);
        return AuthenticationResponseModel.builder()
                .token(token)
                .build();
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
        }).orElseThrow(() -> new NotFoundException("Company Not Found"));
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public void deleteCompany(int id) {
        boolean companyExists = companyRepository.existsById(id);
        if(!companyExists) throw new NotFoundException("Company Not Found");

        companyRepository.deleteById(id);
    }

    public void emailExists(Company company) {
        boolean emailExists = companyRepository.findByEmail(company.getEmail()).stream().anyMatch(existingCompany-> !existingCompany.equals(company));
        if(emailExists) throw new AlreadyExistsException("there is already a registered company with this email");
    }
}
