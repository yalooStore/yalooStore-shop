package com.yaloostore.shop.product.repository.repository;

import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.jpa.JpaProductRepository;
import com.yaloostore.shop.product.repository.dummy.ProductDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaProductRepositoryTest {


    @Autowired
    TestEntityManager entityManager;

    @Autowired
    JpaProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {

        product = ProductDummy.dummy();

    }

    @DisplayName("상품 저장 테스트")
    @Test
    void testSaveProduct(){

        //given
        entityManager.persist(product);

        //when
        Product savedProduct = productRepository.save(product);

        //then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getProductName()).isEqualTo(product.getProductName());

    }

    @DisplayName("특정 Id로 상품 찾는 테스트")
    @Test
    void findByProductId() {
        //given
        Product savedProduct = entityManager.persist(product);

        //when
        Optional<Product> optionalProduct = productRepository.findByProductId(savedProduct.getProductId());


        //then
        assertThat(optionalProduct.isPresent());
        assertThat(optionalProduct.get().getProductName()).isEqualTo(savedProduct.getProductName());

    }



    @DisplayName("특정 id로 상품 삭제 테스트")
    @Test
    void testDeleteByProductId() {
        //given
        Product savedProduct = entityManager.persist(product);

        //when
        productRepository.deleteByProductId(savedProduct.getProductId());
        Optional<Product> optionalProduct = productRepository.findByProductId(savedProduct.getProductId());


        //then
        assertThat(optionalProduct).isNotPresent();

    }

    @DisplayName("모든 상품 유형을 조회하는 테스트")
    @Test
    void testFindAll(){

        //given
        entityManager.persist(product);

        //when
        List<Product> products = productRepository.findAll();

        //then
        assertThat(products.isEmpty()).isFalse();
        assertThat(products.size()).isOne();
        assertThat(products.get(0).getProductName()).isEqualTo("dummy product");
    }



}