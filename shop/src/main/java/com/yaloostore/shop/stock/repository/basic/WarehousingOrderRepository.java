package com.yaloostore.shop.stock.repository.basic;

import com.yaloostore.shop.stock.entity.BranchOffice;
import com.yaloostore.shop.stock.entity.BranchOfficeWarehousingOrder;
import com.yaloostore.shop.stock.repository.common.BranchOfficeCommonRepository;
import com.yaloostore.shop.stock.repository.common.WarehousingOrderCommonRepository;
import org.springframework.data.repository.Repository;

public interface WarehousingOrderRepository extends Repository<BranchOfficeWarehousingOrder,Long>, WarehousingOrderCommonRepository {


}
