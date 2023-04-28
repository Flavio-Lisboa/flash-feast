package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.mapper.CompanyMapper;
import com.flavio.flashfeast.api.model.CompanyModel;
import com.flavio.flashfeast.api.model.input.CompanyInput;
import com.flavio.flashfeast.domain.entities.Company;
import com.flavio.flashfeast.domain.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CompanyModel> createCompany(@Valid @RequestBody CompanyInput companyInput) {
        Company company = companyMapper.toEntity(companyInput);
        CompanyModel companyResponse =  companyMapper.toModel(companyService.createCompany(company));
        return ResponseEntity.ok(companyResponse);
    }

    @GetMapping
    public ResponseEntity<List<CompanyModel>> findAll() {
        List<CompanyModel> companies = companyMapper.toCollectionModel(companyService.findAll());
        return ResponseEntity.ok(companies);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable int id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().body("deleted company");
    }
}
