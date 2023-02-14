package com.yaloostore.shop.product.product_type.repository;

import com.yaloostore.shop.product.product_type.dummy.ProductTypeDummy;
import com.yaloostore.shop.product.product_type.entity.ProductType;
import com.yaloostore.shop.product.product_type.repository.jpa.JpaProductTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaProductTypeRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    JpaProductTypeRepository jpaProductTypeRepository;

    private ProductType productType;


    @BeforeEach
    void setUp() {
        productType = ProductTypeDummy.dummy();
    }


    @DisplayName("상품 타입 저장  테스트")
    @Test
    void testSaveProductType(){
        //given
        ProductType savedProductType = entityManager.persist(productType);

        //when
        ProductType save = jpaProductTypeRepository.save(savedProductType);
        Optional<ProductType> optionalProductType = jpaProductTypeRepository.findByProductTypeNo(savedProductType.getProductTypeNo());

        //then
        assertThat(optionalProductType.isPresent()).isTrue();
        assertThat(optionalProductType.get().getProductTypeName()).isEqualTo(productType.getProductTypeName());

    }

    @DisplayName("특정 상품 타입 번호를 사용해서 상품 타입을 찾는 테스트")
    @Test
    void findByProductTypeNo() {
        //given
        ProductType savedProductType = entityManager.persist(productType);

        //when
        Optional<ProductType> optionalProductType = jpaProductTypeRepository.findByProductTypeNo(savedProductType.getProductTypeNo());

        //then
        assertThat(optionalProductType.isPresent()).isTrue();
        assertThat(optionalProductType.get().getProductTypeName()).isEqualTo("book");
    }




}