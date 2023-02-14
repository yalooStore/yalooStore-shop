package com.yaloostore.shop.product.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_image")
public class ProductImage {
    @Id
    @Column(name = "product_image_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productImageNo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "productNo", nullable = false)
    private Product product;




}
