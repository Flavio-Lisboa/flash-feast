package com.flavio.flashfeast.domain.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.flavio.flashfeast.domain.entities.Company;
import com.flavio.flashfeast.domain.enums.Role;
import com.flavio.flashfeast.domain.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final Cloudinary cloudinary;

    public CompanyService(CompanyRepository companyRepository, Cloudinary cloudinary) {
        this.companyRepository = companyRepository;
        this.cloudinary = cloudinary;
    }

    public Company createCompany(Company company, MultipartFile logo) {
        System.out.println(company);
        String logoUrl = uploadFile(logo);
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

    private String uploadFile(MultipartFile logo) {
        try {
            File uploadedLogo = convertMultipartToFile(logo);
            Map<?, ?> uploadResult = cloudinary.uploader().upload(uploadedLogo, ObjectUtils.asMap("folder", "company_logo"));
            boolean isDeleted = uploadedLogo.delete();

            if (isDeleted)System.out.println("File successfully deleted");
            else System.out.println("File doesn't exist");

            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultipartToFile(MultipartFile logo) throws IOException {
        File convertedLogo = new File(Objects.requireNonNull(logo.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(convertedLogo);
        fileOutputStream.write(logo.getBytes());
        fileOutputStream.close();
        return convertedLogo;
    }
}
