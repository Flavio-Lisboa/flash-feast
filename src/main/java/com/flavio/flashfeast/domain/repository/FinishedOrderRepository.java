package com.flavio.flashfeast.domain.repository;

import com.flavio.flashfeast.domain.entities.FinishedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinishedOrderRepository extends JpaRepository<FinishedOrder, Integer> {

    @Query("SELECT f FROM FinishedOrder f WHERE f.company.id = :idCompany")
    List<FinishedOrder> getAllByCompanyId(int idCompany);
}
