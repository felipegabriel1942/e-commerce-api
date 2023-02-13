package com.felipegabriel.ecommerceapi.controller;

import com.felipegabriel.ecommerceapi.dto.SaleDTO;
import com.felipegabriel.ecommerceapi.model.entity.User;
import com.felipegabriel.ecommerceapi.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SaleDTO> create(
            @RequestBody @Valid SaleDTO saleDTO,
            @AuthenticationPrincipal User user
    ) {
        SaleDTO sale = saleService.create(saleDTO, user);
        return new ResponseEntity<>(sale, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> cancel(@PathVariable("id") Long id) {
        saleService.cancel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<SaleDTO>> findSales(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @AuthenticationPrincipal User user
    ) {
        Page<SaleDTO> sales =  saleService.findSalesByUser(user.getEmail(), page, size);
        return new ResponseEntity<>(sales, sales.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
