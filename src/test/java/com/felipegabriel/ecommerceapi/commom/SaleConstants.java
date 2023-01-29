package com.felipegabriel.ecommerceapi.commom;

import static com.felipegabriel.ecommerceapi.commom.ProductConstants.*;

import com.felipegabriel.ecommerceapi.dto.SaleDTO;
import com.felipegabriel.ecommerceapi.enums.SaleStatus;
import com.felipegabriel.ecommerceapi.model.entity.Sale;

import java.time.LocalDate;
import java.util.Arrays;

public class SaleConstants {

    public static final Sale SALE = Sale.builder()
            .date(LocalDate.now())
            .status(SaleStatus.ACTIVE)
            .user(UserConstants.USER)
            .products(Arrays.asList(PRODUCT))
            .build();

    public static final SaleDTO SALE_DTO = SaleDTO.builder()
            .date(LocalDate.now())
            .status(SaleStatus.ACTIVE)
            .user(UserConstants.USER_DTO)
            .products(Arrays.asList(PRODUCT_DTO))
            .build();
}
