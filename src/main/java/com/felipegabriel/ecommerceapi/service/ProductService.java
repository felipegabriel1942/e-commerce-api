package com.felipegabriel.ecommerceapi.service;

import com.felipegabriel.ecommerceapi.dto.ProductDTO;
import com.felipegabriel.ecommerceapi.exception.ProductNotFoundException;
import com.felipegabriel.ecommerceapi.mapper.ProductMapper;
import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public Product create(ProductDTO productDTO) {
        return productRepository.save(productMapper.toEntity(productDTO));
    }

    public Product update(ProductDTO productDTO) {
        this.findById(productDTO.getId());
        return productRepository.save(productMapper.toEntity(productDTO));
    }

    public List<ProductDTO> findByName(String name) {
        List<ProductDTO> products = productRepository.findByName(name).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

        if (products.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return products;
    }

    public Page<ProductDTO> findProducts(Integer page, Integer size) {
        Page<ProductDTO> products = productRepository
                .findAll(PageRequest.of(page, size))
                .map(productMapper::toDto);

        if (products.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return products;
    }

    protected Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException());
    }
}
