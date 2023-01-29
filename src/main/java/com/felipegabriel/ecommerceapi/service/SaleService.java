package com.felipegabriel.ecommerceapi.service;

import com.felipegabriel.ecommerceapi.dto.ProductDTO;
import com.felipegabriel.ecommerceapi.dto.SaleDTO;
import com.felipegabriel.ecommerceapi.enums.SaleStatus;
import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.model.entity.Sale;
import com.felipegabriel.ecommerceapi.model.entity.User;
import com.felipegabriel.ecommerceapi.model.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;

    public Sale create(SaleDTO saleDTO) {
        var sale = Sale.builder()
                .date(LocalDate.now())
                .user(
                        User.builder()
                                .id(saleDTO.getUser().getId())
                                .email(saleDTO.getUser().getEmail())
                                .role(saleDTO.getUser().getRole())
                                .build()
                )
                .products(mapToProductEntityList(saleDTO.getProducts()))
                .status(SaleStatus.ACTIVE)
                .build();

        return saleRepository.save(sale);
    }

    // TODO: Implementar mapper
    private List<Product> mapToProductEntityList(List<ProductDTO> productDTOList) {
        return productDTOList.stream()
                .map(this::mapToProductEntity)
                .collect(Collectors.toList());
    }

    private Product mapToProductEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .price(productDTO.getPrice())
                .name(productDTO.getName())
                .build();
    }
}
