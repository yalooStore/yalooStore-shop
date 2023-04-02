package com.yaloostore.shop.stock.repository.common;

import com.yaloostore.shop.stock.entity.BranchOffice;
import com.yaloostore.shop.stock.entity.BranchOfficeWarehousingOrder;

public interface WarehousingOrderCommonRepository {

    BranchOfficeWarehousingOrder save(BranchOfficeWarehousingOrder branchOfficeWarehousingOrder);

    BranchOfficeWarehousingOrder findByWarehousingId(Long id);



}
