package com.felipegabriel.ecommerceapi.commom;

import com.felipegabriel.ecommerceapi.dto.ProductDTO;
import com.felipegabriel.ecommerceapi.enums.ProductStatus;
import com.felipegabriel.ecommerceapi.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Arrays;

public class ProductConstants {

    public static final Product PRODUCT = Product.builder()
            .name("PLAYSTATION 5")
            .status(ProductStatus.ACTIVE)
            .price(BigDecimal.valueOf(5000))
            .imageUrl("http://image.com.br")
            .build();

    public static final Product INACTIVE_PRODUCT = Product.builder()
            .name("XBOX-360")
            .status(ProductStatus.INACTIVE)
            .price(BigDecimal.valueOf(1000))
            .imageUrl("http://image.com.br")
            .build();;

    public static final ProductDTO PRODUCT_DTO = ProductDTO.builder()
            .name("PLAYSTATION 5")
            .status(ProductStatus.ACTIVE)
            .price(BigDecimal.valueOf(5000))
            .imageUrl("http://image.com.br")
            .build();

    public static final Product INACTIVE_PRODUCT_DTO = Product.builder()
            .name("XBOX-360")
            .status(ProductStatus.INACTIVE)
            .price(BigDecimal.valueOf(1000))
            .imageUrl("http://image.com.br")
            .build();;

    public static final Page<ProductDTO> PAGE_PRODUCT_DTO = new PageImpl<>(
            Arrays.asList(PRODUCT_DTO),
            PageRequest.of(0, 1),
            1
    );

    public static final Page<Product> PAGE_PRODUCT = new PageImpl<>(
            Arrays.asList(PRODUCT),
            PageRequest.of(0, 1),
            1
    );

}
