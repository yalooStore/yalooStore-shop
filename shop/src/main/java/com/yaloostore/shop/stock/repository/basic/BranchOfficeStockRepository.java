package com.yaloostore.shop.stock.repository.basic;

import com.yaloostore.shop.stock.entity.BranchOffice;
import com.yaloostore.shop.stock.entity.BranchOfficeStock;
import com.yaloostore.shop.stock.repository.common.BranchOfficeCommonRepository;
import com.yaloostore.shop.stock.repository.common.BranchOfficeStockCommonRepository;
import org.springframework.data.repository.Repository;

public interface BranchOfficeStockRepository extends Repository<BranchOfficeStock,Long>, BranchOfficeStockCommonRepository {


}
