package com.yaloostore.shop.product.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProductStorage {


    @Id
    @Column(name = "product_storage_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productStorageId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Column(nullable = false)
    private Long quantity;
    

}
