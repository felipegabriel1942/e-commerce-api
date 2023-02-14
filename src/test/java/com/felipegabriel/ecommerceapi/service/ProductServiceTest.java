package com.felipegabriel.ecommerceapi.service;

import static org.assertj.core.api.Assertions.*;
import static com.felipegabriel.ecommerceapi.commom.ProductConstants.*;
import static org.mockito.Mockito.*;

import com.felipegabriel.ecommerceapi.dto.ProductDTO;
import com.felipegabriel.ecommerceapi.exception.ProductNotFoundException;
import com.felipegabriel.ecommerceapi.mapper.ProductMapper;
import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.model.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
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

    @Mock
    private ProductMapper productMapper;

    @Test
    public void createProduct_WithValidData_ReturnsEmpty() {
        when(productRepository.save(PRODUCT)).thenReturn(PRODUCT);
        when(productMapper.toEntity(PRODUCT_DTO)).thenReturn(PRODUCT);

        Product sut = productService.create(PRODUCT_DTO);

        assertThat(sut).isEqualTo(PRODUCT);
    }

    @Test
    public void getProducts_ByExistingName_ReturnsProducts() {
        when(productRepository.findByName(anyString())).thenReturn(Arrays.asList(PRODUCT));
        when(productMapper.toDto(PRODUCT)).thenReturn(PRODUCT_DTO);

        List<ProductDTO> sut = productService.findByName(anyString());

        assertThat(sut).isNotEmpty();
        assertThat(sut.size()).isEqualTo(PAGE_PRODUCT.getTotalElements());
        assertThat(sut.get(0)).isEqualTo(PRODUCT_DTO);
    }

    @Test
    public void getProducts_ByUnexistingName_ThrowsException() {
        when(productRepository.findByName(anyString())).thenReturn(Arrays.asList());

        assertThatThrownBy(() -> productService.findByName(anyString())).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    public void getProducts_BySizeAndPage_ReturnsProductsPage() {
        when(productRepository.findAll(PageRequest.of(PAGE_PRODUCT_DTO.getNumber(), PAGE_PRODUCT.getSize())))
                .thenReturn(PAGE_PRODUCT);
        when(productMapper.toDto(PRODUCT)).thenReturn(PRODUCT_DTO);

        Page<ProductDTO> sut = productService.findProducts(PAGE_PRODUCT_DTO.getNumber(), PAGE_PRODUCT.getSize());

        assertThat(sut.get()).isNotEmpty();
        assertThat(sut.get().collect(Collectors.toList()).size()).isEqualTo(PAGE_PRODUCT.getTotalElements());
        assertThat(sut.get().collect(Collectors.toList()).get(0)).isEqualTo(PRODUCT_DTO);
    }

    @Test
    public void getProducts_ByUnexistingSizeAndPage_ThrowsException() {
        when(productRepository.findAll(PageRequest.of(PAGE_PRODUCT_DTO.getNumber(), PAGE_PRODUCT_DTO.getSize())))
                .thenReturn(Page.empty());

        assertThatThrownBy(() -> productService.findProducts(PAGE_PRODUCT_DTO.getNumber(), PAGE_PRODUCT_DTO.getSize()))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
