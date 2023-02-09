package com.felipegabriel.ecommerceapi.repository;

import static org.assertj.core.api.Assertions.*;

import com.felipegabriel.ecommerceapi.commom.ProductConstants;
import com.felipegabriel.ecommerceapi.model.entity.Product;
import com.felipegabriel.ecommerceapi.model.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createProduct_WithValidData_ReturnsProduct() {
        Product product = productRepository.save(ProductConstants.PRODUCT);

        Product sut = testEntityManager.find(Product.class, product.getId());

        assertThat(sut).isNotNull();
    }

    @Test
    public void createProduct_WithInvalidData_ThrowsException() {
        Product emptyProduct = new Product();
        Product invalidProduct = new Product(null, "", BigDecimal.ZERO);

        assertThatThrownBy(() ->  productRepository.save(emptyProduct)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() ->  productRepository.save(invalidProduct)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void findProduct_ByName_ReturnsProductsList() {
        productRepository.save(ProductConstants.PRODUCT);

        List<Product> sut = productRepository.findByName(ProductConstants.PRODUCT.getName());

        assertThat(sut.isEmpty()).isFalse();
        assertThat(sut.size()).isEqualTo(1);
        assertThat(sut.get(0)).isEqualTo(ProductConstants.PRODUCT);
    }
}
