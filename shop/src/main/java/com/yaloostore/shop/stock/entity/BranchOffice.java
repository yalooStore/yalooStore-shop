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
public class BranchOffice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "branch_office_id")
    private Long branchOfficeId;


    @JoinColumn(name ="branch_office_stock_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private BranchOfficeStock branchOfficeStock;


    @Column(name = "branch_office_name", nullable = false)
    private String branchOfficeName;


    @Column(name = "branch_office_address")
    private String branchOfficeAddress;


    //분점, 지점 전화번호
    @Column(name = "branch_office_tel")
    private String branchOfficeTel;


    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted;
    public BranchOffice softDelete(){

        return BranchOffice.builder()
                .branchOfficeId(this.branchOfficeId)
                .branchOfficeStock(this.branchOfficeStock)
                .branchOfficeName("DELETED BRANCH OFFICE")
                .branchOfficeAddress("DELETED BRANCH OFFICE")
                .branchOfficeTel("DELETED BRANCH OFFICE")
                .isDeleted(true)
                .build();
    }

}
