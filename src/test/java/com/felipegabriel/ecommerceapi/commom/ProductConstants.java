package com.felipegabriel.ecommerceapi.commom;

import com.felipegabriel.ecommerceapi.dto.ProductDTO;
import com.felipegabriel.ecommerceapi.model.entity.Product;

import java.math.BigDecimal;

public class ProductConstants {

    public static final Product PRODUCT = Product.builder().name("PLAYSTATION 5").price(BigDecimal.valueOf(5000)).build();

    public static final ProductDTO PRODUCT_DTO = ProductDTO.builder().name("PLAYSTATION 5").price(BigDecimal.valueOf(5000)).build();

}
