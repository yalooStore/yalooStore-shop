package com.yaloostore.shop.stock.entity;

import com.yaloostore.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지점, 분점의 상품 재고 보유 상태를 보여주는 엔티티입니다.
 * */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BranchOfficeStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "branch_office_stock_id")
    private Long branchOfficeStockId;


    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;


    @Column(nullable = false)
    private Long quantity;


}
