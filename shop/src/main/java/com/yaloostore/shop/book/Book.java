package com.yaloostore.shop.book;


import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.product_type.entity.ProductType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "book")
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @MapsId("productId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Column(name = "isbn", nullable = false, columnDefinition = "varchar(30)", unique = true)
    private String isbn;

    @Column(name = "page_count", nullable = false)
    private Long pageCount;

    @Column(name = "book_created_at", nullable = false)
    private LocalDateTime bookCreatedAt;

    @Column(name = "is_ebook", nullable = false)
    private Boolean isEbook;

    @Column(name = "ebook_url", columnDefinition = "text")
    private String ebookUrl;

    @Column(name = "publisher_name", nullable = false, columnDefinition = "varchar(20)")
    private String publisherName;

    @Column(name = "author_name", nullable = false, columnDefinition = "varchar(255)")
    private String authorName;

}
