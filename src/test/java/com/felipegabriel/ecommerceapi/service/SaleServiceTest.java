package com.felipegabriel.ecommerceapi.service;

import static org.mockito.Mockito.*;
import static com.felipegabriel.ecommerceapi.commom.SaleConstants.*;
import static org.assertj.core.api.Assertions.*;

import com.felipegabriel.ecommerceapi.model.entity.Sale;
import com.felipegabriel.ecommerceapi.model.repository.SaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

    @InjectMocks
    private SaleService saleService;

    @Mock
    private SaleRepository saleRepository;

    @Test
    public void createSale_WithValidData_ReturnsSale() {
        when(saleRepository.save(SALE)).thenReturn(SALE);

        Sale sut = saleService.create(SALE_DTO);

        assertThat(sut).isEqualTo(SALE);
    }
}
