package com.yaloostore.shop.product.repository.querydsl;

import com.yaloostore.shop.book.Book;
import com.yaloostore.shop.book.dummy.BookDummy;
import com.yaloostore.shop.product.dto.response.ProductFindResponse;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.dummy.ProductDummy;
import com.yaloostore.shop.product.repository.querydsl.QuerydslProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
class QuerydslProductRepositoryTest {


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private QuerydslProductRepository repository;

    private Product product;
    private Book book;

    @BeforeEach
    void setUp() {
        product = ProductDummy.dummy();
        entityManager.persist(product);

        book = BookDummy.dummy(product);
        entityManager.persist(book);

    }

    @DisplayName("관리자용 상품 정보 모두 가져오기")
    @Test
    void testQueryFindAllProductsAdmin() {

        //when
        Page<ProductFindResponse> productsAdmin = repository.queryFindAllProductsAdmin(PageRequest.of(0, 5));


        //then
        assertThat(productsAdmin).isNotNull();
        assertThat(productsAdmin.getContent().get(0).getIsEbook()).isTrue();
    }


    @DisplayName("사용자용 상품 정보 모두 가져오기 - isExpose가 true인 것만 가져오기")
    @Test
    void queryFindAllProductsUser() {

        //given
        Product product2 = ProductDummy.dummy2();
        entityManager.persist(product2);

        Book book2 = BookDummy.dummy2(product2);
        entityManager.persist(book2);


        //when
        Page<ProductFindResponse> productsUser = repository.queryFindAllProductsUser(PageRequest.of(0, 5));


        //then
        assertThat(productsUser).isNotNull();
        assertThat(productsUser.getContent().get(0)).isNotNull();
        assertThat(productsUser.getContent().size()).isOne();

    }


    @DisplayName("특정 상품 아이디로 해당하는 상품 정보 가져오기")
    @Test
    void testQueryFindProductById() {
        //given
        Long productNo = product.getProductId();

        //when
        Optional<ProductFindResponse> optionalProduct = repository.queryFindProductById(productNo);

        //then
        assertThat(optionalProduct.isPresent());
        assertThat(optionalProduct.get().getAuthorName()).isEqualTo(book.getAuthorName());
        assertThat(optionalProduct.get().getProductName()).isEqualTo(product.getProductName());
    }
}