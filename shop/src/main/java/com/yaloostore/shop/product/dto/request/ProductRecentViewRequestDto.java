package com.yaloostore.shop.product.dto.request;


import com.yaloostore.shop.product.entity.Product;
import lombok.*;

import java.util.List;


/**
 * 사용자가 최근 본 상품의 정보 요청에 사용되는 request dto 클래스입니다.
 * */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ProductRecentViewRequestDto {
    private List<Long> totalIds;
    private List<Long> pageIds;


}
