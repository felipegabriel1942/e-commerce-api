package com.felipegabriel.ecommerceapi.repository;

import static org.assertj.core.api.Assertions.*;
import static com.felipegabriel.ecommerceapi.commom.SaleConstants.*;

import com.felipegabriel.ecommerceapi.commom.ProductConstants;
import com.felipegabriel.ecommerceapi.commom.UserConstants;
import com.felipegabriel.ecommerceapi.model.entity.Sale;
import com.felipegabriel.ecommerceapi.model.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Collectors;

@DataJpaTest
public class SaleRepositoryTest {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void beforeEach() {
        SALE.setId(null);
        SALE.setSaleProducts(null);
        SALE.getUser().setId(null);
    }

    @Test
    public void createSale_WithValidData_ReturnsSale() {
        testEntityManager.persistFlushFind(UserConstants.USER);

        Sale savedSale = saleRepository.save(SALE);

        Sale sut = testEntityManager.find(Sale.class, savedSale.getId());

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(savedSale);
    }

    @Test
    public void createSale_WithInvalidData_ThrowsException() {
        Sale emptySale = new Sale();

        assertThatThrownBy(() -> saleRepository.save(emptySale)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void findSale_ByUser_ReturnsSalesPage() {
        testEntityManager.persistFlushFind(UserConstants.USER);
        testEntityManager.persistFlushFind(ProductConstants.PRODUCT);

        Sale savedSale = testEntityManager.persistFlushFind(SALE);

        Page<Sale> sut = saleRepository.findSalesByUser(savedSale.getUser().getEmail(), PageRequest.of(0, 1));

        assertThat(sut.isEmpty()).isFalse();
        assertThat(sut.get().collect(Collectors.toList()).size()).isEqualTo(1);
        assertThat(sut.get().collect(Collectors.toList()).get(0)).isEqualTo(savedSale);

    }
}
