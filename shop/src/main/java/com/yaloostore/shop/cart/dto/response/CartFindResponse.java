package com.yaloostore.shop.cart.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartFindResponse {

    private Long productId;

    private LocalDateTime placedInCartTime;
}
