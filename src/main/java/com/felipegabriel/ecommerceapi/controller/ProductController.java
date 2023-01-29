package com.felipegabriel.ecommerceapi.controller;

import com.felipegabriel.ecommerceapi.dto.ProductDTO;
import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> create(@RequestBody ProductDTO productDTO) {
        Product product = productService.create(productDTO);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("name/{name}")
    public ResponseEntity<List<Product>> findByName(@PathVariable("name") String name) {
        List<Product> product = productService.findByName(name);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> findProducts(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        Page<Product> products = productService.findProducts(page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
