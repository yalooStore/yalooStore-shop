package com.yaloostore.shop.stock.repository.dummy;

import com.yaloostore.shop.stock.entity.BranchOffice;
import com.yaloostore.shop.stock.entity.BranchOfficeStock;

public class BranchOfficeDummy {

    public static BranchOffice dummy(BranchOfficeStock branchOfficeStock){
        return BranchOffice.builder()
                .branchOfficeStock(branchOfficeStock)
                .branchOfficeName("test")
                .branchOfficeAddress("adress")
                .branchOfficeTel("tel")
                .isDeleted(false)
                .build();

    }
}
