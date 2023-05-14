package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.mapper.CompanyLocationMapper;
import com.flavio.flashfeast.api.model.CompanyLocationModel;
import com.flavio.flashfeast.api.model.input.CompanyLocationInput;
import com.flavio.flashfeast.domain.entities.CompanyLocation;
import com.flavio.flashfeast.domain.service.CompanyLocationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company-location")
public class CompanyLocationController {

    private final CompanyLocationService companyLocationService;
    private final CompanyLocationMapper companyLocationMapper;

    public CompanyLocationController(CompanyLocationService companyLocationService, CompanyLocationMapper companyLocationMapper) {
        this.companyLocationService = companyLocationService;
        this.companyLocationMapper = companyLocationMapper;
    }

    @GetMapping("/{idCompany}")
    public ResponseEntity<CompanyLocationModel> getLocation(@PathVariable int idCompany) {
        CompanyLocation companyLocation = companyLocationService.getUserLocation(idCompany);

        CompanyLocationModel companyLocationModel = companyLocationMapper.toModel(companyLocation);
        return ResponseEntity.ok(companyLocationModel);
    }

    @PostMapping("/{idCompany}")
    public ResponseEntity<CompanyLocationModel> createCompanyLocation(
            @PathVariable int idCompany,
            @Valid @RequestBody CompanyLocationInput companyLocationInput) {
        CompanyLocation companyLocation = companyLocationMapper.toEntity(companyLocationInput);
        CompanyLocation companyLocationResponse = companyLocationService.createCompanyLocation(idCompany, companyLocation);

        CompanyLocationModel companyLocationModel = companyLocationMapper.toModel(companyLocationResponse);
        return ResponseEntity.ok(companyLocationModel);
    }

    @PutMapping("/{idCompany}")
    public ResponseEntity<CompanyLocationModel> updateCompanyLocation(
            @PathVariable int idCompany,
            @Valid @RequestBody CompanyLocationInput companyLocationInput) {
        CompanyLocation companyLocation = companyLocationMapper.toEntity(companyLocationInput);
        CompanyLocation companyLocationResponse = companyLocationService.updateUserLocation(idCompany, companyLocation);

        CompanyLocationModel companyLocationModel = companyLocationMapper.toModel(companyLocationResponse);
        return ResponseEntity.ok(companyLocationModel);
    }

    @DeleteMapping("/{idCompany}")
    public ResponseEntity<?> deleteCompanyLocation(@PathVariable int idCompany) {
        companyLocationService.deleteCompanyLocation(idCompany);
        return ResponseEntity.ok().build();
    }
}
