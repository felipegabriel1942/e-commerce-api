package com.felipegabriel.ecommerceapi.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.felipegabriel.ecommerceapi.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product")
@Where(clause = "status='ACTIVE'")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    @DecimalMin(value = "0.1", inclusive = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVE;

    @NotEmpty
    @NotNull
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<SaleProduct> saleProducts = new ArrayList<>();

    @PrePersist
    void prePersist() {
        if (status == null) {
            status = ProductStatus.ACTIVE;
        }
    }
}
