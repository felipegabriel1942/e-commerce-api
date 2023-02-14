package com.felipegabriel.ecommerceapi.service;

import static com.felipegabriel.ecommerceapi.commom.SaleConstants.*;
import static com.felipegabriel.ecommerceapi.commom.UserConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.felipegabriel.ecommerceapi.dto.SaleDTO;
import com.felipegabriel.ecommerceapi.enums.SaleStatus;
import com.felipegabriel.ecommerceapi.exception.SaleNotFoundException;
import com.felipegabriel.ecommerceapi.mapper.SaleMapper;
import com.felipegabriel.ecommerceapi.model.repository.SaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

    @InjectMocks
    private SaleService saleService;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SaleMapper saleMapper;


    @Test
    public void createSale_WithValidData_ReturnsSale() {
        when(saleRepository.save(any())).thenReturn(SALE);
        when(saleMapper.toDto(SALE)).thenReturn(SALE_DTO);
        when(saleMapper.toEntity(SALE_DTO)).thenReturn(SALE);

        SaleDTO sut = saleService.create(SALE_DTO, USER);

        assertThat(sut).isEqualTo(SALE_DTO);
    }

    @Test
    public void updateSale_WithCancelStatus_ReturnsSale() {
        when(saleRepository.findById(anyLong())).thenReturn(Optional.of(SALE));
        when(saleRepository.save(any())).thenReturn(SALE);

        SALE_DTO.setStatus(SaleStatus.CANCELED);
        when(saleMapper.toDto(SALE)).thenReturn(SALE_DTO);

        SaleDTO sut = saleService.cancel(anyLong());

        assertThat(sut.getStatus()).isEqualTo(SaleStatus.CANCELED);
    }

    @Test
    public void updateSale_WithUnexistingId_ThrowsException() {
        when(saleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> saleService.cancel(anyLong())).isInstanceOf(SaleNotFoundException.class);
    }

    @Test
    public void getSales_BySizeAndPage_ReturnsSalesPage() {
        when(saleRepository
                .findSalesByUser(USER.getEmail(), PageRequest.of(PAGE_SALE.getNumber(), PAGE_SALE.getSize())))
                    .thenReturn(PAGE_SALE);
        when(saleMapper.toDto(SALE)).thenReturn(SALE_DTO);

        Page<SaleDTO> sut = saleService.findSalesByUser(USER.getEmail(), PAGE_SALE.getNumber(), PAGE_SALE.getSize());

        assertThat(sut.isEmpty()).isEqualTo(false);
        assertThat(sut.getNumberOfElements()).isEqualTo(PAGE_SALE.getTotalElements());
        assertThat(sut.get()).isEqualTo(Arrays.asList(SALE_DTO));
    }

    @Test
    public void getSales_ByUnexistingSizeAndPage_ThrowsException() {
        when(saleRepository
                .findSalesByUser(USER.getEmail(), PageRequest.of(PAGE_SALE.getNumber(), PAGE_SALE.getSize())))
                .thenReturn(Page.empty());

        assertThatThrownBy(() -> saleService.findSalesByUser(USER.getEmail(), PAGE_SALE.getNumber(), PAGE_SALE.getSize()))
                .isInstanceOf(SaleNotFoundException.class);
    }
}
