package com.felipegabriel.ecommerceapi.controller;

import com.felipegabriel.ecommerceapi.dto.ProductDTO;
import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.service.ProductService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create product",
            description = "Create a product to be used on sales. Only an admin can create it."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = ProductDTO.class), mediaType = "application/json")})
    })
    public ResponseEntity<Product> create(@RequestBody @Valid ProductDTO productDTO) {
        Product product = productService.create(productDTO);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update product",
            description = "Update a product to be used on sales. Only an admin can update it."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = ProductDTO.class), mediaType = "application/json")
                    })
    })
    public ResponseEntity<Product> update(@RequestBody @Valid ProductDTO productDTO) {
        Product product = productService.update(productDTO);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("name/{name}")
    @Operation(
            summary = "Find products by name",
            description = "Find all products whose name matches the string passed by the user. Anyone logged or not can use it."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = ProductDTO.class), mediaType = "application/json")
                    })
    })
    public ResponseEntity<List<ProductDTO>> findByName(@PathVariable("name") String name) {
        List<ProductDTO> product = productService.findByName(name);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    @Operation(
      summary = "Find products",
      description = "Find products on paginated query. Anyone logged or not can use it."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = ProductDTO.class), mediaType = "application/json")
                    })
    })
    public ResponseEntity<Page<ProductDTO>> findProducts(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        Page<ProductDTO> products = productService.findProducts(page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
