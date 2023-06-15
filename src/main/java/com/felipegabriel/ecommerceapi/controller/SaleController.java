package com.felipegabriel.ecommerceapi.controller;

import com.felipegabriel.ecommerceapi.dto.SaleDTO;
import com.felipegabriel.ecommerceapi.model.entity.User;
import com.felipegabriel.ecommerceapi.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Sale", description = "Sale management APIs")
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Create a sale",
            description = "Creates a sale containing a list of products. Only logged in users can perform operation."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = SaleDTO.class), mediaType = "application/json")
                    })
    })
    public ResponseEntity<SaleDTO> create(
            @RequestBody @Valid SaleDTO saleDTO,
            @AuthenticationPrincipal User user
    ) {
        SaleDTO sale = saleService.create(saleDTO, user);
        return new ResponseEntity<>(sale, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Cancel sale",
            description = "Cancel sale of logged user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(), mediaType = "application/json")
                    })
    })
    public ResponseEntity<Void> cancel(@PathVariable("id") Long id) {
        saleService.cancel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Search sales",
            description = "Search the sales of the logged in user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = SaleDTO.class), mediaType = "application/json")
                    })
    })
    public ResponseEntity<Page<SaleDTO>> findSales(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @AuthenticationPrincipal User user
    ) {
        Page<SaleDTO> sales =  saleService.findSalesByUser(user.getEmail(), page, size);
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }
}
