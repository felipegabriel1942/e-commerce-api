package com.felipegabriel.ecommerceapi.repository;

import static org.assertj.core.api.Assertions.*;
import static com.felipegabriel.ecommerceapi.commom.ProductConstants.*;

import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.model.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void beforeEach() {
        PRODUCT.setId(null);
        PRODUCT.setSaleProducts(null);
    }

    @Test
    public void createProduct_WithValidData_ReturnsProduct() {
        Product product = productRepository.save(PRODUCT);

        Product sut = testEntityManager.find(Product.class, product.getId());

        assertThat(sut).isNotNull();
    }

    @Test
    public void createProduct_WithInvalidData_ThrowsException() {
        Product emptyProduct = new Product();
        Product invalidProduct = new Product(null, "", BigDecimal.ZERO, null, null, null);

        assertThatThrownBy(() ->  productRepository.save(emptyProduct)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() ->  productRepository.save(invalidProduct)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void findProduct_ByName_ReturnsProductsList() {
        Product savedProduct = testEntityManager.persistFlushFind(PRODUCT);

        List<Product> sut = productRepository.findByName(PRODUCT.getName());

        assertThat(sut.isEmpty()).isFalse();
        assertThat(sut.size()).isEqualTo(1);
        assertThat(sut.get(0)).isEqualTo(savedProduct);
    }


    @Test
    public void findAllProducts_ByStatus_ReturnsActiveProductsList() {
        List<Product> products = Arrays.asList(PRODUCT, INACTIVE_PRODUCT);

        products.forEach(product -> testEntityManager.persist(product));

        Page<Product> sut = productRepository.findAll(PageRequest.of(0, 10));

        assertThat(sut.isEmpty()).isFalse();
        assertThat(sut.getNumberOfElements()).isEqualTo(1);
    }
}
