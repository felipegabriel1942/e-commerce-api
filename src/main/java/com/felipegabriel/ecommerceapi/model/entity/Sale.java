package com.felipegabriel.ecommerceapi.model.entity;

import com.felipegabriel.ecommerceapi.enums.SaleStatus;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sale")
@Builder
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", updatable = false)
    private LocalDate date;

    @JoinColumn(name = "_user")
    @ManyToOne
    private User user;

    @ManyToMany(targetEntity = Product.class)
    private List<Product> products;

    @Enumerated(EnumType.STRING)
    private SaleStatus status = SaleStatus.ACTIVE;

    @PrePersist
    void prePersist() {
        if (this.status == null) {
            this.status = SaleStatus.ACTIVE;
        }

        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }
}
