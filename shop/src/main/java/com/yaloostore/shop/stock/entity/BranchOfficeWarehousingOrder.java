package com.yaloostore.shop.stock.entity;


import com.yaloostore.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionId;

import java.time.LocalDateTime;

/**
 * 분점, 지점의 입출고 요청을 위한 엔티티입니다.
 * */

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BranchOfficeWarehousingOrder {

    //입출고 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "warehousing_id")
    private Long warehousingId;

    //입출고 여부(true -> 입고, false -> 출고)
    private Boolean isWarehousing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_office_id")
    private BranchOffice branchOffice;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Column(nullable = false)
    private Long quantity;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    //출고, 입고 사유
    @Column(name = "warehousing_reason", columnDefinition = "text", length = 1000)
    private String warehousingReason;

}
