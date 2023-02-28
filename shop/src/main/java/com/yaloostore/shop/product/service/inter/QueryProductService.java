package com.yaloostore.shop.product.service.inter;

import com.yaloostore.shop.product.dto.response.ProductBookNewOneResponse;

import java.util.List;

public interface QueryProductService {

    List<ProductBookNewOneResponse> getProductBookByNewOne();
}
