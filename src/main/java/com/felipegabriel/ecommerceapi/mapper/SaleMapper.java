package com.felipegabriel.ecommerceapi.mapper;

import com.felipegabriel.ecommerceapi.dto.SaleDTO;
import com.felipegabriel.ecommerceapi.model.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    SaleDTO toDto(Sale sale);

    Sale toEntity(SaleDTO saleDTO);
}
