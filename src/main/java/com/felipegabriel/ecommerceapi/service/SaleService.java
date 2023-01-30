package com.felipegabriel.ecommerceapi.service;

import com.felipegabriel.ecommerceapi.dto.ProductDTO;
import com.felipegabriel.ecommerceapi.dto.SaleDTO;
import com.felipegabriel.ecommerceapi.enums.SaleStatus;
import com.felipegabriel.ecommerceapi.exception.SaleNotFoundException;
import com.felipegabriel.ecommerceapi.mapper.SaleMapper;
import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.model.entity.Sale;
import com.felipegabriel.ecommerceapi.model.entity.User;
import com.felipegabriel.ecommerceapi.model.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;

    private final SaleMapper saleMapper;

    public SaleDTO create(SaleDTO saleDTO) {
        return saleMapper.toDto(saleRepository.save(saleMapper.toEntity(saleDTO)));
    }

    public SaleDTO cancel(Long id) {
        Optional<Sale> sale = saleRepository.findById(id);

        if (sale.isEmpty()) {
            throw new SaleNotFoundException();
        }

        sale.get().setStatus(SaleStatus.CANCELED);
        return saleMapper.toDto(saleRepository.save(sale.get()));
    }
}
