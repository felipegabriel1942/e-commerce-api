package com.felipegabriel.ecommerceapi.controller;

import static com.felipegabriel.ecommerceapi.commom.SaleConstants.*;
import static com.felipegabriel.ecommerceapi.commom.UserConstants.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipegabriel.ecommerceapi.exception.SaleNotFoundException;
import com.felipegabriel.ecommerceapi.model.entity.Sale;
import com.felipegabriel.ecommerceapi.security.JwtAuthenticationFilter;
import com.felipegabriel.ecommerceapi.service.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(SaleController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SaleService saleService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final String BASE_URL = "/api/v1/sale";

    @BeforeEach
    public void beforeEach() {
        Authentication auth = new UsernamePasswordAuthenticationToken(USER, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @WithMockUser(username = "user@email.com")
    public void createSale_WithValidData_ReturnsSale() throws Exception {
        when(saleService.create(SALE_DTO, USER)).thenReturn(SALE_DTO);

        mockMvc.perform(post(BASE_URL)
                    .content(objectMapper.writeValueAsString(SALE_DTO))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(SALE_DTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user@email.com")
    public void createSale_WithInvalidData_ThrowsException() throws Exception {
        mockMvc.perform(post(BASE_URL).content(objectMapper.writeValueAsString(new Sale()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "user@email.com")
    public void updateSale_WithCancelStatus_ReturnsOK() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + 1)
                        .content(objectMapper.writeValueAsString(SALE_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@email.com")
    public void updateSale_WithUnexistingId_ReturnsNotFound() throws Exception {
        when(saleService.cancel(1L)).thenThrow(SaleNotFoundException.class);

        mockMvc.perform(delete(BASE_URL + "/" + 1)
                        .content(objectMapper.writeValueAsString(SALE_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@email.com")
    public void listSales_ByPageAndSize_ReturnPagedSales() throws Exception {
        when(saleService.findSalesByUser(USER.getEmail(), PAGE_SALE_DTO.getNumber(), PAGE_SALE_DTO.getSize()))
                .thenReturn(PAGE_SALE_DTO);

        mockMvc.perform(get(BASE_URL + String.format("?page=%s&size=%s", PAGE_SALE_DTO.getNumber(), PAGE_SALE_DTO.getSize())))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(PAGE_SALE_DTO)));
    }

    @Test
    @WithMockUser(username = "user@email.com")
    public void listSales_ByUnexistingPage_ReturnsNotFound() throws Exception {
        when(saleService.findSalesByUser(USER.getEmail(), PAGE_SALE_DTO.getNumber(), PAGE_SALE_DTO.getSize()))
                .thenThrow(SaleNotFoundException.class);

        mockMvc.perform(get(BASE_URL + String.format("?page=%s&size=%s", PAGE_SALE_DTO.getNumber(), PAGE_SALE_DTO.getSize())))
                .andExpect(status().isNotFound());
    }
}
