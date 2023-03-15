package com.yaloostore.shop.cart.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;


/**
 * 상품을 장바구니에 저장할 때 사용할 Response dto 클래스입니다.
 * */
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CartSaveResponse {


    private Long productId;
    private LocalDateTime placedInCartTime;


}
