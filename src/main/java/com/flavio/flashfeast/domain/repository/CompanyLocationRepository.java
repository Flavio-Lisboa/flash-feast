package com.flavio.flashfeast.domain.repository;

import com.flavio.flashfeast.domain.entities.CompanyLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyLocationRepository extends JpaRepository<CompanyLocation, Integer> {
}
