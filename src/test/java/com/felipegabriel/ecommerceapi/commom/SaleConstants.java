package com.felipegabriel.ecommerceapi.commom;

import static com.felipegabriel.ecommerceapi.commom.ProductConstants.*;

import com.felipegabriel.ecommerceapi.dto.SaleDTO;
import com.felipegabriel.ecommerceapi.enums.SaleStatus;
import com.felipegabriel.ecommerceapi.model.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Arrays;

public class SaleConstants {

    public static final Sale SALE = Sale.builder()
            .date(LocalDate.now())
            .status(SaleStatus.ACTIVE)
            .user(UserConstants.USER)
            .build();

    public static final SaleDTO SALE_DTO = SaleDTO.builder()
            .date(LocalDate.now())
            .status(SaleStatus.ACTIVE)
            .products(Arrays.asList(PRODUCT_DTO))
            .build();

    public static final Page<SaleDTO> PAGE_SALE_DTO = new PageImpl<>(
            Arrays.asList(SALE_DTO),
            PageRequest.of(0, 1),
            1
    );

    public static final Page<Sale> PAGE_SALE = new PageImpl<>(
            Arrays.asList(SALE),
            PageRequest.of(0, 1),
            1
    );
}
