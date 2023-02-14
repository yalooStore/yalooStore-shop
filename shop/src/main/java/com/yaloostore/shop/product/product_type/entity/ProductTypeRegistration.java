package com.yaloostore.shop.product.product_type.entity;


import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.product_type.entity.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "product_type_registration")
public class ProductTypeRegistration {

    @EmbeddedId
    private ProductTypeRegistrationPk productTypeRegistrationPk;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @MapsId("productId")
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @MapsId("productTypeNo")
    @JoinColumn(name = "product_type_no", nullable = false)
    private ProductType productType;


    @NoArgsConstructor
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    @Embeddable
    public static class ProductTypeRegistrationPk implements Serializable {
        private Long productId;
        private Long productTypeNo;

    }
}
