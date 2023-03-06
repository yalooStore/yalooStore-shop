package com.yaloostore.shop.product.service.inter;

import com.yaloostore.shop.product.dto.response.ProductTypeResponseDto;

import java.util.List;

public interface ProductTypeService {


    /**
     * 상품 유형을 조회할 때 사용하는 메소드입니다.
     *
     * @return productType을 List<dto> 객체로 만들어 돌려줍니다.
     * */
    List<ProductTypeResponseDto> findAllProductTypes();
}
