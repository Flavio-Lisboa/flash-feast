package com.flavio.flashfeast.domain.repository;

import com.flavio.flashfeast.domain.entities.CurrentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentOrderRepository extends JpaRepository<CurrentOrder, Integer> {
}
