package com.felipegabriel.ecommerceapi.dto;

import com.felipegabriel.ecommerceapi.enums.SaleStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDTO {

    private Long id;

    private LocalDate date;

    @NotNull(message = "Venda deve ter pelo menos um produto.")
    private List<ProductDTO> products;

    private SaleStatus status;
}
