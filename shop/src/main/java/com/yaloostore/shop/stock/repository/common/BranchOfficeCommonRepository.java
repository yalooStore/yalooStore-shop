package com.yaloostore.shop.stock.repository.common;

import com.yaloostore.shop.stock.entity.BranchOffice;

public interface BranchOfficeCommonRepository {

    BranchOffice save(BranchOffice branchOffice);

    BranchOffice findByBranchOfficeId(Long id);



}
