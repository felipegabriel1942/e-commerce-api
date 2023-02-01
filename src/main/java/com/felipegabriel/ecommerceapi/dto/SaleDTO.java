package com.felipegabriel.ecommerceapi.dto;

import com.felipegabriel.ecommerceapi.enums.SaleStatus;
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

    private List<ProductDTO> products;

    private SaleStatus status;
}
