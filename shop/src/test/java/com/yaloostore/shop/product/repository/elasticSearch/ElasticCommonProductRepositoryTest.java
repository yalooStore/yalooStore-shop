package com.yaloostore.shop.product.repository.elasticSearch;

import com.yaloostore.shop.product.documents.SearchProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ElasticCommonProductRepositoryTest {

    @Autowired
    ElasticCommonProductRepository repository;
    private SearchProduct searchProduct;

    private Pageable pageable = PageRequest.of(0,1);

    @BeforeEach
    void setUp() {

        searchProduct = SearchProduct.builder()
                .productId(324L)
                .productName("test")
                .stock(100L)
                .productCreatedAt(LocalDateTime.now())
                .description("test 설명 주절주절 주절 주절")
                .thumbnailUrl("test url")
                .fixedPrice(1000L)
                .rawPrice(1100L)
                .isSelled(false)
                .isDeleted(false)
                .isExpose(true)
                .discountPercent(10L)
                .build();
    }


    @Test
    void testSave(){

        //given
        SearchProduct savedProduct = repository.save(searchProduct);

        Optional<SearchProduct> optionalSearchProduct = repository.findById(savedProduct.getProductId());

        assertThat(optionalSearchProduct.get()).isNotNull();
        assertThat(optionalSearchProduct.get().getProductId()).isEqualTo(324L);
    }

    @DisplayName("상품 이름으로 해당하는 제품 찾는 테스트 - 성공")
    @Test
    void testFindByProductName() {



    }
}