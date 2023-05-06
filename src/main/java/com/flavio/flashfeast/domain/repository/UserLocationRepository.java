package com.flavio.flashfeast.domain.repository;

import com.flavio.flashfeast.domain.entities.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, Integer> {
}
