package com.felipegabriel.ecommerceapi.service;

import static com.felipegabriel.ecommerceapi.commom.SaleConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.felipegabriel.ecommerceapi.enums.SaleStatus;
import com.felipegabriel.ecommerceapi.exception.SaleNotFoundException;
import com.felipegabriel.ecommerceapi.model.entity.Sale;
import com.felipegabriel.ecommerceapi.model.repository.SaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

    @InjectMocks
    private SaleService saleService;

    @Mock
    private SaleRepository saleRepository;

    @Test
    public void createSale_WithValidData_ReturnsSale() {
        when(saleRepository.save(any())).thenReturn(SALE);

        Sale sut = saleService.create(SALE_DTO);

        assertThat(sut).isEqualTo(SALE);
    }

    @Test
    public void updateSale_WithCancelStatus_ReturnsSale() {
        when(saleRepository.findById(anyLong())).thenReturn(Optional.of(SALE));
        when(saleRepository.save(any())).thenReturn(SALE);

        Sale sut = saleService.cancel(anyLong());

        assertThat(sut.getStatus()).isEqualTo(SaleStatus.CANCELED);
    }

    @Test
    public void updateSale_WithUnexistingId_ThrowsException() {
        when(saleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> saleService.cancel(anyLong())).isInstanceOf(SaleNotFoundException.class);
    }
}
