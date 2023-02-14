package com.felipegabriel.ecommerceapi.service;

import com.felipegabriel.ecommerceapi.dto.SaleDTO;
import com.felipegabriel.ecommerceapi.enums.SaleStatus;
import com.felipegabriel.ecommerceapi.exception.SaleNotFoundException;
import com.felipegabriel.ecommerceapi.mapper.SaleMapper;
import com.felipegabriel.ecommerceapi.model.entity.Sale;
import com.felipegabriel.ecommerceapi.model.entity.User;
import com.felipegabriel.ecommerceapi.model.repository.SaleRepository;
import com.felipegabriel.ecommerceapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;

    private final SaleMapper saleMapper;

    private final UserRepository userRepository;

    public SaleDTO create(SaleDTO saleDTO, User user) {
        Sale sale = saleMapper.toEntity(saleDTO);
        sale.setUser(user);

        return saleMapper.toDto(saleRepository.save(sale));
    }

    public SaleDTO cancel(Long id) {
        Optional<Sale> sale = saleRepository.findById(id);

        if (sale.isEmpty()) {
            throw new SaleNotFoundException();
        }

        sale.get().setStatus(SaleStatus.CANCELED);
        return saleMapper.toDto(saleRepository.save(sale.get()));
    }

    public Page<SaleDTO> findSalesByUser(String email, Integer page, Integer size) {
        Page<SaleDTO> sales = saleRepository
                .findSalesByUser(email, PageRequest.of(page, size))
                .map(saleMapper::toDto);

        if (sales.isEmpty()) {
            throw new SaleNotFoundException();
        }

        return sales;
    }
}
