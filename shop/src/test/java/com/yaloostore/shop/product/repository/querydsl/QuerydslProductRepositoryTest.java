package com.yaloostore.shop.product.repository.querydsl;

import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.book.dummy.BookDummy;
import com.yaloostore.shop.product.dto.response.ProductBookNewStockResponse;
import com.yaloostore.shop.product.dto.response.ProductFindResponse;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.common.ProductTypeCode;
import com.yaloostore.shop.product.repository.dummy.ProductDummy;
import com.yaloostore.shop.product.repository.basic.ProductCommonRepository;
import com.yaloostore.shop.product.repository.querydsl.inter.QuerydslProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;


@Transactional
@SpringBootTest
@Slf4j
class QuerydslProductRepositoryTest {


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private QuerydslProductRepository querydslProductRepository;
    private ProductCommonRepository productRepository;

    private Product product;
    private Product bestSellerProduct;

    private Book book;

    @BeforeEach
    void setUp() {
        product = ProductDummy.dummy();
        entityManager.persist(product);


        bestSellerProduct = ProductDummy.dummy2();
        entityManager.persist(bestSellerProduct);

        book = BookDummy.dummy(product);
        entityManager.persist(book);

    }

    @DisplayName("관리자용 상품 정보 모두 가져오기")
    @Test
    void testQueryFindAllProductsAdmin() {

        //when
        Page<ProductFindResponse> productsAdmin = querydslProductRepository.queryFindAllProductsAdmin(PageRequest.of(0, 5));


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
        Page<ProductFindResponse> productsUser = querydslProductRepository.queryFindAllProductsUser(PageRequest.of(0, 5));


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
        Optional<ProductFindResponse> optionalProduct = querydslProductRepository.queryFindProductById(productNo);

        //then
        assertThat(optionalProduct.isPresent());
        assertThat(optionalProduct.get().getAuthorName()).isEqualTo(book.getAuthorName());
        assertThat(optionalProduct.get().getProductName()).isEqualTo(product.getProductName());
    }


    @DisplayName("새로 등록된 도서 상품 정보 가져오기 - 10개로 한정지음")
    @Test
    void testQueryFindProductNewOne(){

        //when
        List<ProductBookNewStockResponse> response = querydslProductRepository.queryFindProductNewOne();

        assertThat(response).isNotNull();
        assertThat(response.get(0).getIsbn()).isEqualTo(book.getIsbn());


    }

    @DisplayName("모든 도서 상품 정보 조회하기 - 사용자용")
    @Test
    void testQueryFindAllProduct(){


        Product product1 = Product
                .builder()
                .productName("testtest")
                .isExpose(true)
                .productTypeCode(ProductTypeCode.NONE)
                .description("ddasdadas")
                .stock(10L)
                .rawPrice(1_000L)
                .isSelled(false)
                .thumbnailUrl("dsdadas")
                .discountPercent(10L)
                .fixedPrice(1000L)
                .isDeleted(false)
                .productCreatedAt(LocalDateTime.now())
                .build();
        Book book1 = Book.builder()
                .product(product1)
                .isbn("dsadsd")
                .isEbook(false)
                .authorName("test")
                .pageCount(100L)
                .publisherName("publisherName")
                .build();

        entityManager.persist(product1);
        entityManager.persist(book1);


        //when
        Page<Product> products = querydslProductRepository.queryFindAllProduct(PageRequest.of(0,5));


        //then
        assertThat(products).isNotNull();
        assertThat(products.getSize()).isEqualTo(5L);

        assertThat(products.getContent().get(0).getIsExpose()).isTrue();

    }
}