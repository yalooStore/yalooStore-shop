package com.yaloostore.shop.order.repository.querydsl;

import com.yaloostore.shop.book.Book;
import com.yaloostore.shop.book.dummy.BookDummy;
import com.yaloostore.shop.order.dto.response.BestSellerResponse;
import com.yaloostore.shop.order.dummy.OrderDummy;
import com.yaloostore.shop.order.dummy.OrderProductDummy;
import com.yaloostore.shop.order.entity.Order;
import com.yaloostore.shop.order.entity.OrderProduct;
import com.yaloostore.shop.order.repository.querydsl.inter.QuerydslOrderProductRepository;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.dummy.ProductDummy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class QuerydslOrderProductRepositoryImplTest {


    @PersistenceContext
    EntityManager entityManager;


    @Autowired
    QuerydslOrderProductRepository querydslOrderProductRepository;

    private Order order;
    private Product product;
    private Book book;
    private OrderProduct orderProduct;

    @BeforeEach
    void setUp(){

        order = OrderDummy.dummy_nonMember();
        product = ProductDummy.dummy();
        book = BookDummy.dummy(product);
        orderProduct= OrderProductDummy.dummy(product, order);
    }


    @DisplayName("중복되는 product id로 판매량이 제일 높은 상품 찾아오기")
    @Test
    void testQueryFindAllByBestSeller(){
        //given
        entityManager.persist(order);
        entityManager.persist(product);
        entityManager.persist(book);
        entityManager.persist(orderProduct);


        //when
        List<BestSellerResponse> responseList = querydslOrderProductRepository.queryFindAllByBestSeller();


        //then
        assertThat(responseList.isEmpty()).isFalse();
        assertThat(responseList.get(0).getIsbn()).isEqualTo(book.getIsbn());

    }

}