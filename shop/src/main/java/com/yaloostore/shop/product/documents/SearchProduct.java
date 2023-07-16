//package com.yaloostore.shop.product.documents;
//
//
//import com.yaloostore.shop.helper.Indices;
//import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.*;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Date;
//
///**
// * 엘라스틱 서치에 사용되는 인덱스 입니다.(관계형 디비 table - 엘라스틱서치 index)
// * */
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@ToString
////TODO: 엘라스틱서치 작업 진행해보자 ~
//@Setting(settingPath = "static/elastic/elastic-settings.json")
//@Mapping(mappingPath = "static/elastic/product-mapping.json")
//@Document(indexName = Indices.PRODUCTS_INDEX)
//public class SearchProduct {
//
//
//    @Id
//    @Field(name = "product_id", type = FieldType.Keyword)
//    private Long productId;
//
//    @Field(name = "product_name", type = FieldType.Text)
//    private String productName;
//
//    @Field(type = FieldType.Long)
//    private Long stock;
//
//    //ex) basic_date =  2023 01 11
//    @Field(name = "product_created_at", type=FieldType.Date, format = DateFormat.basic_date)
//    private LocalDate productCreatedAt;
//
//    @Field(type=FieldType.Text)
//    private String description;
//
//    @Field(name = "thumbnail_url",type=FieldType.Text)
//    private String thumbnailUrl;
//
//    @Field(name = "fixed_price", type=FieldType.Long)
//    private Long fixedPrice;
//
//    @Field(name = "raw_price", type=FieldType.Long)
//    private Long rawPrice;
//
//    @Field(name = "is_sold", type=FieldType.Boolean)
//    private Boolean isSold;
//
//    @Field(name = "is_deleted", type=FieldType.Boolean)
//    private Boolean isDeleted;
//
//    @Field(name ="is_expose", type=FieldType.Boolean)
//    private Boolean isExpose;
//
//    @Field(name = "discount_percent", type=FieldType.Long)
//    private Long discountPercent;
//
//
//}
