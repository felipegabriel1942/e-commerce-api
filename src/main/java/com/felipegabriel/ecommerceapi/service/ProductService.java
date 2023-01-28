package com.felipegabriel.ecommerceapi.service;

import com.felipegabriel.ecommerceapi.dto.ProductDTO;
import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(ProductDTO productDTO) {
        var product = Product.builder()
                .price(productDTO.getPrice())
                .name(productDTO.getName())
                .build();

        return productRepository.save(product);
    }
}
