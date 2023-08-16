package com.yaloostore.shop.book.repository.common;

import com.yaloostore.shop.book.dummy.BookDummy;
import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.book.repository.basic.BookCommonRepository;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.dummy.ProductDummy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local-test")
class CommonBookRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    BookCommonRepository bookCommonRepository;


    private Book book;
    private Product product;

    @BeforeEach
    void setUp(){
        product = ProductDummy.dummy2();
        book = BookDummy.dummy(product);
        product.setBook(book);
    }

    @DisplayName("도서 저장 테스트 - 성공")
    @Test
    void save(){

        //given
        String isbn = book.getIsbn();
        Book persist = entityManager.persist(book);

        //when
        Book save = bookCommonRepository.save(book);


        //then
        assertThat(save.getIsbn()).isEqualTo(isbn);
        assertThat(save.getIsbn()).isEqualTo(persist.getIsbn());


    }

    @DisplayName("도서 저장 테스트 - 성공")
    @Test
    void existsByIsbn(){
        //given
        String isbn = book.getIsbn();
        Book persist = entityManager.persist(book);

        //when
        boolean result = bookCommonRepository.existsByIsbn(isbn);
        boolean falseResult = bookCommonRepository.existsByIsbn("dadasddasda");


        //then
        assertThat(result).isTrue();
        assertThat(falseResult).isFalse();

    }


    @DisplayName("Isbn을 사용해서 해당 도서 상품 정보를 가져오는 메서드 테스트 - 성공")
    @Test
    void findBookByIsbn(){
        //given
        String isbn = book.getIsbn();
        Book persist = entityManager.persist(book);

        //when
        Book bookByIsbn = bookCommonRepository.findBookByIsbn(isbn);

        //then
        assertThat(bookByIsbn.getAuthorName()).isEqualTo(bookByIsbn.getAuthorName());
    }


}