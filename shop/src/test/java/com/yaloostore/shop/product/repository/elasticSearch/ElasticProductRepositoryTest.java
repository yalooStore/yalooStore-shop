package com.yaloostore.shop.product.repository.elasticSearch;

import com.yaloostore.shop.product.documents.SearchProduct;
import com.yaloostore.shop.product.repository.basic.SearchProductRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("local-test")
class ElasticProductRepositoryTest {


    @Autowired
    private SearchProductRepository searchProductRepository;

    @Autowired
    private ElasticCommonProductRepository elasticCommonProductRepository;

    private Pageable pageable = PageRequest.of(0,1);

    private SearchProduct searchProduct;


    @BeforeEach
    void setUp() {
        searchProduct = SearchProduct.builder()
                .productId(-1L)
                .productName("test")
                .stock(10L)
                .productCreatedAt(LocalDate.now())
                .description("content")
                .thumbnailUrl("test url")
                .fixedPrice(900L)
                .rawPrice(1000L)
                .isSold(false)
                .isDeleted(false)
                .discountPercent(10L)
                .build();


    }


    @DisplayName("상품 이름으로 검색 테스트")
    @Test
    void searchProductsByProductName() {

        //given
        elasticCommonProductRepository.save(searchProduct);


        //when
        Page<SearchProduct> searchProducts = searchProductRepository.searchProductsByProductName("test", pageable);


        //then
        assertThat(searchProducts.getContent().size()).isEqualTo(1L);
        assertThat(searchProducts.getContent().get(0).getProductName()).isEqualTo("test");
    }


}