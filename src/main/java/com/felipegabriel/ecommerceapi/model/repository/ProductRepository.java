package com.felipegabriel.ecommerceapi.model.repository;

import com.felipegabriel.ecommerceapi.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where lower(p.name) like lower(concat('%', :name, '%'))")
    List<Product> findByName(String name);
}
