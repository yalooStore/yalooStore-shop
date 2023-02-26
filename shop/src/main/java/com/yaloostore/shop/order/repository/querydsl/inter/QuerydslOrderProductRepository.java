package com.yaloostore.shop.order.repository.querydsl.inter;

import com.yaloostore.shop.order.dto.response.BestSellerResponse;

import java.util.List;
import java.util.Optional;

public interface QuerydslOrderProductRepository {
    List<BestSellerResponse> queryFindAllByBestSeller();


}
