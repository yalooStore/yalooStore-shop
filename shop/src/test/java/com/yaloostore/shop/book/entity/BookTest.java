package com.yaloostore.shop.book.entity;

import com.yaloostore.shop.book.dummy.BookDummy;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.dummy.ProductDummy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
class BookTest {

    @Test
    void setBookPubDate() {
        //given
        Product product = ProductDummy.dummy2();
        Book book = BookDummy.dummy(product);

        String date = "20220101";

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate stringToLocalDateTime = LocalDate.parse(date, format);

        //when
        book.setBookPubDate(date);

        //then
        Assertions.assertThat(book.getBookCreatedAt()).isEqualTo(stringToLocalDateTime);
        System.out.println(book.getBookCreatedAt());

    }
}