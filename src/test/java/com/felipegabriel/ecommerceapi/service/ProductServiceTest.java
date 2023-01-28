package com.felipegabriel.ecommerceapi.service;

import static  org.assertj.core.api.Assertions.*;

import com.felipegabriel.ecommerceapi.commom.ProductConstants;
import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.model.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void createProduct_WithValidData_ReturnsEmpty() {
        Mockito
                .when(productRepository.save(ProductConstants.PRODUCT))
                .thenReturn(ProductConstants.PRODUCT);

        Product sut = productService.create(ProductConstants.PRODUCT_DTO);

        assertThat(sut).isEqualTo(ProductConstants.PRODUCT);
    }

    @Test
    public void getProducts_ByExistingName_ReturnsProducts() {
        Mockito
                .when(productRepository.findByName(Mockito.anyString()))
                .thenReturn(Arrays.asList(ProductConstants.PRODUCT));

        List<Product> sut = productService.findByName(Mockito.anyString());

        assertThat(sut).isNotEmpty();
        assertThat(sut.size()).isEqualTo(1);
        assertThat(sut.get(0)).isEqualTo(ProductConstants.PRODUCT);
    }
}
