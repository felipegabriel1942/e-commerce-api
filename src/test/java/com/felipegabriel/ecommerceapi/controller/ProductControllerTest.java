package com.felipegabriel.ecommerceapi.controller;

import static com.felipegabriel.ecommerceapi.commom.ProductConstants.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.felipegabriel.ecommerceapi.exception.ProductNotFoundException;
import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.security.JwtAuthenticationFilter;
import com.felipegabriel.ecommerceapi.service.ProductService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final String BASE_URL = "/api/v1/product";

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void createProduct_WithValidData_ReturnsProduct() throws Exception {
        when(productService.create(PRODUCT_DTO)).thenReturn(PRODUCT);

        mockMvc.perform(post(BASE_URL)
                    .content(objectMapper.writeValueAsString(PRODUCT_DTO))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(PRODUCT)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void updateProduct_WithValidData_ReturnsProduct() throws Exception {
        when(productService.update(PRODUCT_DTO)).thenReturn(PRODUCT);

        mockMvc.perform(put(BASE_URL)
                        .content(objectMapper.writeValueAsString(PRODUCT_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(PRODUCT)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void createProduct_WithInvalidData_ReturnsProduct() throws Exception {
        Product emptyProduct = new Product();
        Product invalidProduct = new Product(null, "", null, null, null, null);

        mockMvc.perform(post(BASE_URL)
                        .content(objectMapper.writeValueAsString(emptyProduct))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post(BASE_URL)
                        .content(objectMapper.writeValueAsString(invalidProduct))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void listProducts_ByExistingName_ReturnsFilteredProducts() throws Exception {
         when(productService.findByName(PRODUCT_DTO.getName())).thenReturn(Arrays.asList(PRODUCT_DTO));

        mockMvc.perform(get(BASE_URL + "/name/" + PRODUCT_DTO.getName())
                        .content(objectMapper.writeValueAsString(PRODUCT_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(PRODUCT_DTO))))
                .andExpect(status().isOk());
    }

    @Test
    public void listProducts_ByUnexistingName_ReturnsNotFound() throws Exception {
        when(productService.findByName(PRODUCT_DTO.getName()))
                .thenThrow(ProductNotFoundException.class);

        mockMvc.perform(get(BASE_URL +"/name/" + PRODUCT_DTO.getName()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listProducts_ByPageAndSize_ReturnsPagedProducts() throws Exception {
        when(productService.findProducts(PAGE_PRODUCT_DTO.getNumber(), PAGE_PRODUCT_DTO.getSize()))
                .thenReturn(PAGE_PRODUCT_DTO);

        mockMvc.perform(get(BASE_URL + "?" + String.format("page=%s&size=%s", PAGE_PRODUCT_DTO.getNumber(), PAGE_PRODUCT_DTO.getSize())))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(PAGE_PRODUCT_DTO)));
    }

    @Test
    public void listProducts_ByUnexistingPage_ReturnsNotFound() throws Exception {
        when(productService.findProducts(PAGE_PRODUCT_DTO.getNumber(), PAGE_PRODUCT_DTO.getSize()))
                .thenThrow(ProductNotFoundException.class);

        mockMvc.perform(get(BASE_URL + "?" + String.format("page=%s&size=%s", PAGE_PRODUCT_DTO.getNumber(), PAGE_PRODUCT_DTO.getSize())))
                .andExpect(status().isNotFound());
    }
}
