package com.felipegabriel.ecommerceapi.model.repository;

import com.felipegabriel.ecommerceapi.model.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("select s from Sale s where s.user.email = :userEmail")
    Page<Sale> findSalesByUser(String userEmail, PageRequest pageRequest);
}
