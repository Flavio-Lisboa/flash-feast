package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.domain.entities.CompanyLocation;
import com.flavio.flashfeast.domain.exception.AlreadyExistsException;
import com.flavio.flashfeast.domain.exception.NotFoundException;
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
        return companyLocationRepository.findById(idCompany).orElseThrow(() -> new NotFoundException("Location Not Found"));
    }

    public CompanyLocation createCompanyLocation(int idCompany, CompanyLocation companyLocation) {
        boolean company = companyRepository.existsById(idCompany);
        if(!company) throw new NotFoundException("Company Not Found");

        boolean exists = companyLocationRepository.existsById(idCompany);
        if(exists) throw new AlreadyExistsException("Location Already Exists");

        companyLocation.setCompanyId(idCompany);
        return companyLocationRepository.save(companyLocation);
    }

    public CompanyLocation updateUserLocation(int idCompany, CompanyLocation companyLocation) {
        boolean exists = companyLocationRepository.existsById(idCompany);
        if(!exists) throw new NotFoundException("Location Not Found");

        companyLocation.setCompanyId(idCompany);
        return companyLocationRepository.save(companyLocation);
    }

    public void deleteCompanyLocation(int idCompany) {
        boolean exists = companyLocationRepository.existsById(idCompany);
        if(!exists) throw new NotFoundException("Location Not Found");

        companyLocationRepository.deleteById(idCompany);
    }
}
