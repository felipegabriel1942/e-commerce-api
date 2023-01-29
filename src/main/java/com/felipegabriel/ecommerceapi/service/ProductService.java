package com.felipegabriel.ecommerceapi.service;

import com.felipegabriel.ecommerceapi.dto.ProductDTO;
import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public Page<Product> findProducts(Integer page, Integer size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }
}
