package com.yaloostore.shop.stock.repository.basic;

import com.yaloostore.shop.stock.entity.BranchOffice;
import com.yaloostore.shop.stock.repository.common.BranchOfficeCommonRepository;
import org.springframework.data.repository.Repository;

public interface BranchOfficeRepository extends Repository<BranchOffice,Long>, BranchOfficeCommonRepository {



}
