package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.mapper.CompanyMapper;
import com.flavio.flashfeast.api.model.CompanyModel;
import com.flavio.flashfeast.api.model.input.CompanyInput;
import com.flavio.flashfeast.domain.entities.Company;
import com.flavio.flashfeast.domain.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @PostMapping
    public ResponseEntity<CompanyModel> createCompany(@Valid @ModelAttribute CompanyInput companyInput, @RequestPart("logo") MultipartFile logo) {
        Company company = companyMapper.toEntity(companyInput);
        CompanyModel companyResponse =  companyMapper.toModel(companyService.createCompany(company, logo));
        return ResponseEntity.ok(companyResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyModel> updateCompany(@PathVariable int id, @Valid @RequestBody CompanyInput companyInput) {
        Company company = companyMapper.toEntity(companyInput);
        Company companyResponse = companyService.updateCompany(id, company);

        if(companyResponse == null) return ResponseEntity.notFound().build();

        CompanyModel companyModel = companyMapper.toModel(companyResponse);
        return ResponseEntity.ok(companyModel);
    }

    @GetMapping
    public ResponseEntity<List<CompanyModel>> findAll() {
        List<Company> companies = companyService.findAll();
        if(companies.isEmpty()) return ResponseEntity.noContent().build();

        List<CompanyModel> companiesModel = companyMapper.toCollectionModel(companies);
        return ResponseEntity.ok(companiesModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable int id) {
        boolean companyExists = companyService.deleteCompany(id);

        if(companyExists) return ResponseEntity.ok().build();
        else return ResponseEntity.notFound().build();
    }
}
