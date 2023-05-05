package com.flavio.flashfeast.domain.repository;

import com.flavio.flashfeast.domain.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m WHERE m.id = :idMenu AND m.company.id = :idCompany")
    Optional<Menu> findMenu(int idCompany, int idMenu);

    @Query("SELECT m FROM Menu m WHERE m.company.id = :idCompany")
    List<Menu> getMenusByCompanyId(int idCompany);

    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id = :idMenu AND m.company.id = :idCompany")
    int deleteMenuByCompanyId(int idMenu, int idCompany);
}
