package com.yaloostore.shop.product.product_type.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_type")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProductType {


    @Id
    @Column(name = "product_type_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productTypeNo;


    @Column(name = "product_type_name")
    private String productTypeName;



}
