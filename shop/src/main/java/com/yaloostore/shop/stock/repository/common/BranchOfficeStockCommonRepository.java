package com.yaloostore.shop.stock.repository.common;

import com.yaloostore.shop.stock.entity.BranchOffice;
import com.yaloostore.shop.stock.entity.BranchOfficeStock;

public interface BranchOfficeStockCommonRepository {

    BranchOfficeStock save(BranchOfficeStock branchOfficeStock);

    BranchOfficeStock findByBranchOfficeStockId(Long id);

}
