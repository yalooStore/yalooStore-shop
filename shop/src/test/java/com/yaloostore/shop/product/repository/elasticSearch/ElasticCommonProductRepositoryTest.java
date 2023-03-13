package com.yaloostore.shop.product.repository.elasticSearch;

import com.yaloostore.shop.product.documents.SearchProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
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
                .description("test 설명 주절주절 주절 주절")
                .thumbnailUrl("test url")
                .fixedPrice(1000L)
                .productCreatedAt(LocalDate.of(22,11,4))
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


        //when
        Optional<SearchProduct> optionalSearchProduct = repository.findById(savedProduct.getProductId());


        //then
        assertThat(optionalSearchProduct.get()).isNotNull();
        assertThat(optionalSearchProduct.get().getProductName()).isEqualTo("test");
    }

    @DisplayName("삭제 테스트")
    @Test
    void testDeleteProduct(){

        //given
        SearchProduct save = repository.save(searchProduct);

        //when
        repository.delete(save);


        //then
        assertThat(repository.count()).isZero();
    }

    @DisplayName("상품 이름으로 해당하는 제품 찾는 테스트 - 성공")
    @Test
    void testFindByProductName() {
        //when
        Page<SearchProduct> searchProducts = repository.findByProductName(pageable, "test");

        //then
        assertThat(searchProducts.get()).isNotNull();
        assertThat(searchProducts.getContent().get(0).getProductName()).isEqualTo("test");
    }


}