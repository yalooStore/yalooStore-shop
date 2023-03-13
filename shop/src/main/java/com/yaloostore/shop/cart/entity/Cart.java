package com.yaloostore.shop.cart.entity;


import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.CountQuery;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Cart {

    @Id
    @EmbeddedId
    private Pk pk;

    //상품이 카트에 담긴 시간을 나타내는 칼럼(필드)
    @Column(name = "placed_in_cart_time")
    private LocalDateTime placedInCartTime;


    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;



    /**
     * 카트에 상품이 담기면 사용될 static method 입니다.
     * */
    public static Cart create(Member member, Product product) {
        return Cart.builder()
                .pk(Pk.builder()
                        .memberId(member.getMemberId())
                        .productId(product.getProductId())
                        .build())
                .placedInCartTime(LocalDateTime.now())
                .member(member)
                .product(product)
                .build();


    }

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Builder
    public static class Pk implements Serializable {


        @Column(name = "member_id")
        private Long memberId;

        @Column(name = "product_id")
        private Long productId;


    }
}
