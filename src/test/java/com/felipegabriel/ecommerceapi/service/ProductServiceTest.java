package com.felipegabriel.ecommerceapi.service;

import static org.assertj.core.api.Assertions.*;
import static com.felipegabriel.ecommerceapi.commom.ProductConstants.*;

import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.model.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void createProduct_WithValidData_ReturnsEmpty() {
        Mockito
                .when(productRepository.save(PRODUCT))
                .thenReturn(PRODUCT);

        Product sut = productService.create(PRODUCT_DTO);

        assertThat(sut).isEqualTo(PRODUCT);
    }

    @Test
    public void getProducts_ByExistingName_ReturnsProducts() {
        Mockito
                .when(productRepository.findByName(Mockito.anyString()))
                .thenReturn(Arrays.asList(PRODUCT));

        List<Product> sut = productService.findByName(Mockito.anyString());

        assertThat(sut).isNotEmpty();
        assertThat(sut.size()).isEqualTo(1);
        assertThat(sut.get(0)).isEqualTo(PRODUCT);
    }

    @Test
    public void getProducts_BySizeAndPage_ReturnsProductsPage() {
        Mockito
                .when(productRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(Arrays.asList(PRODUCT)));

        Page<Product> sut = productService.findProducts(0, 10);

        assertThat(sut.get()).isNotEmpty();
        assertThat(sut.get().collect(Collectors.toList()).size()).isEqualTo(1);
        assertThat(sut.get().collect(Collectors.toList()).get(0)).isEqualTo(PRODUCT);
    }
}
