package com.felipegabriel.ecommerceapi.dto;

import com.felipegabriel.ecommerceapi.enums.ProductStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private BigDecimal price;

    private ProductStatus status;

    @NotNull
    @NotEmpty
    private String imageUrl;
}
