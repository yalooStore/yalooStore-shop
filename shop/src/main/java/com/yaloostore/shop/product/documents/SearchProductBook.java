package com.yaloostore.shop.product.documents;


import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.helper.Indices;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 엘라스틱 서치에 사용되는 인덱스 입니다.(관계형 디비 table - 엘라스틱서치 index)
 * */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(indexName = Indices.PRODUCTS_BOOKS_INDEX)
public class SearchProductBook {

    @Id
    @Field(name = "product_id", type = FieldType.Long)
    private Long productId;

    @Field(name = "product_name", type = FieldType.Text)
    private String productName;

    @Field(name = "product_id", type = FieldType.Long)
    private Long stock;

    @Field(name = "product_created_at", type =FieldType.Date)
    private LocalDate productCreatedAt;

    @Field(type=FieldType.Text)
    private String description;

    @Field(name = "thumbnail_url",type=FieldType.Text)
    private String thumbnailUrl;

    @Field(name = "fixed_price", type=FieldType.Long)
    private Long fixedPrice;

    @Field(name = "raw_price", type=FieldType.Long)
    private Long rawPrice;

    @Field(name = "is_selled", type=FieldType.Boolean)
    private Boolean isSelled;

    @Field(name = "is_deleted", type=FieldType.Boolean)
    private Boolean isDeleted;

    @Field(name ="is_expose", type=FieldType.Boolean)
    private Boolean isExpose;

    @Field(name = "discount_percent", type=FieldType.Long)
    private Long discountPercent;


    @Field(name = "book", type = FieldType.Object)
    private Book book;


}
