package com.felipegabriel.ecommerceapi.model.entity;

import com.felipegabriel.ecommerceapi.enums.SaleStatus;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @NotNull
    private User user;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleProduct> saleProducts = new ArrayList<>();

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

    public void addProduct(Product product) {
        SaleProduct saleProduct = new SaleProduct(this, product);

        if (saleProducts == null) {
            this.saleProducts = new ArrayList<>();
        }

        saleProducts.add(saleProduct);

        if (product.getSaleProducts() == null) {
            product.setSaleProducts(new ArrayList<>());
        }

        product.getSaleProducts().add(saleProduct);
    }

    public List<Product> getProducts() {
        return this.saleProducts.stream()
                .map(saleProduct -> {
                    Product product = saleProduct.getProduct();
                    product.setPrice(saleProduct.getProductPrice());
                    return product;
                })
                .collect(Collectors.toList());
    }
}
