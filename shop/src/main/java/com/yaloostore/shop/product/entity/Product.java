package com.yaloostore.shop.product.entity;


import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.product.common.ProductTypeCode;
import com.yaloostore.shop.product.common.converter.ProductTypeConverter;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Product {

    @Id
    @Column(name = "product_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(name = "productName")
    private String productName;

    @Column(nullable = false)
    private Long stock;

    @Column(name = "product_created_at", nullable = false)
    private LocalDateTime productCreatedAt;

    @Column(nullable = false, length = 50000)
    private String description;

    @Column(name = "thumbnail_url", nullable = false)
    private String thumbnailUrl;

    //할인된 가격
    @Column(name = "fixed_price", nullable = false)
    private Long discountPrice;

    //정가
    @Column(name = "raw_price", nullable = false)
    private Long rawPrice;

    @Column(name = "is_selled", nullable = false, columnDefinition = "boolean default false")
    private Boolean isSelled;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @Column(name ="is_expose", nullable = false, columnDefinition = "boolean default false")
    private Boolean isExpose;

    @Column(name = "discount_percent", nullable = false)
    private Long discountPercent;


    //product type 등록과 product list 가져오는 service 구현하기
    @Column(name = "product_type_id")
    @Convert(converter = ProductTypeConverter.class)
    private ProductTypeCode productTypeCode;


    /**
     * 양방향 매핑 시 mappedBy를 사용하는데 이때는 매핑된 곳에서 사용하고 있는 필드명을 넣어준다.
     * */
    @OneToOne(mappedBy = "product", cascade = CascadeType.PERSIST)
    private Book book;



}
