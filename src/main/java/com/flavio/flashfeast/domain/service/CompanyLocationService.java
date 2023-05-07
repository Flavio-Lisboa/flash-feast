package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.domain.entities.CompanyLocation;
import com.flavio.flashfeast.domain.repository.CompanyLocationRepository;
import com.flavio.flashfeast.domain.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CompanyLocationService {

    private final CompanyLocationRepository companyLocationRepository;
    private final CompanyRepository companyRepository;

    public CompanyLocationService(CompanyLocationRepository companyLocationRepository, CompanyRepository companyRepository) {
        this.companyLocationRepository = companyLocationRepository;
        this.companyRepository = companyRepository;
    }

    public CompanyLocation getUserLocation(int idCompany) {
        return companyLocationRepository.findById(idCompany).orElse(null);
    }

    public CompanyLocation createCompanyLocation(int idCompany, CompanyLocation companyLocation) {
        boolean exists = companyLocationRepository.existsById(idCompany);

        if(exists) return null;

        boolean company = companyRepository.existsById(idCompany);

        if(!company) return null;

        companyLocation.setCompanyId(idCompany);
        return companyLocationRepository.save(companyLocation);
    }

    public CompanyLocation updateUserLocation(int idCompany, CompanyLocation companyLocation) {
        boolean exists = companyLocationRepository.existsById(idCompany);

        if(!exists) return null;

        companyLocation.setCompanyId(idCompany);
        return companyLocationRepository.save(companyLocation);
    }

    public boolean deleteCompanyLocation(int idCompany) {
        boolean exists = companyRepository.existsById(idCompany);

        if(!exists) return false;

        companyLocationRepository.deleteById(idCompany);
        return true;
    }
}
