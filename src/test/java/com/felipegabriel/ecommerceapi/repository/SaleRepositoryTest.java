package com.felipegabriel.ecommerceapi.repository;

import static org.assertj.core.api.Assertions.*;

import com.felipegabriel.ecommerceapi.commom.ProductConstants;
import com.felipegabriel.ecommerceapi.commom.SaleConstants;
import com.felipegabriel.ecommerceapi.commom.UserConstants;
import com.felipegabriel.ecommerceapi.model.entity.Sale;
import com.felipegabriel.ecommerceapi.model.repository.ProductRepository;
import com.felipegabriel.ecommerceapi.model.repository.SaleRepository;
import com.felipegabriel.ecommerceapi.model.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.stream.Collectors;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SaleRepositoryTest {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void beforeEach() {
        productRepository.save(ProductConstants.PRODUCT);

        userRepository.save(UserConstants.USER);
    }

    @Test
    public void createSale_WithValidData_ReturnsSale() {
        Sale savedSale = saleRepository.save(SaleConstants.SALE);

        Sale sut = testEntityManager.find(Sale.class, savedSale.getId());

        assertThat(sut).isNotNull();
    }

    @Test
    public void createSale_WithInvalidData_ThrowsException() {
        Sale emptySale = new Sale();

        assertThatThrownBy(() -> saleRepository.save(emptySale)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void findSale_ByUser_ReturnsSalesPage() {
        Sale savedSale = saleRepository.save(SaleConstants.SALE);

        Page<Sale> sut = saleRepository.findSalesByUser(savedSale.getUser().getEmail(), PageRequest.of(0, 1));

        assertThat(sut.isEmpty()).isFalse();
        assertThat(sut.get().collect(Collectors.toList()).size()).isEqualTo(1);
        assertThat(sut.get().collect(Collectors.toList()).get(0)).isEqualTo(savedSale);

    }
}
