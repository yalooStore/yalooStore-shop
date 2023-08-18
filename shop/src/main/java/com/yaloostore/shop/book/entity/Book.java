package com.yaloostore.shop.book.entity;


import com.yaloostore.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "book")
@Getter
public class Book {

    //여기에 자동전략쓰면 안됨!!!!!!!
    @Id
    @Column(name = "product_id")
    private Long productId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "isbn", nullable = false, columnDefinition = "varchar(30)", unique = true)
    private String isbn;

    @Column(name = "page_count", nullable = false)
    private Long pageCount;

    @Column(name = "book_created_at")
    private LocalDate bookCreatedAt;

    @Column(name = "is_ebook", nullable = false)
    private Boolean isEbook;

    @Column(name = "ebook_url", columnDefinition = "text")
    private String ebookUrl;

    @Column(name = "publisher_name", nullable = false, columnDefinition = "varchar(255)")
    private String publisherName;

    @Column(name = "author_name", nullable = false, columnDefinition = "varchar(255)")
    private String authorName;


    /**
     * 해당 도서 상품의 출판일을 번경할 때 사용하는 메서드입니다.
     *
     *
     * */
    public void setBookPubDate(String pubDate) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate stringToLocalDateTime = LocalDate.parse(pubDate, format);

        this.bookCreatedAt = stringToLocalDateTime;

    }
}
