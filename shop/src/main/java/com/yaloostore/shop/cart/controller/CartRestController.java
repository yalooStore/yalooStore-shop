package com.yaloostore.shop.cart.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.cart.dto.response.CartSaveResponse;
import com.yaloostore.shop.cart.service.inter.CartService;
import com.yaloostore.shop.common.aspect.annotation.LoginId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service/cart")
public class CartRestController {

    private final CartService cartService;

    @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_USER'})")
    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<CartSaveResponse> save(
            @LoginId(required = true) String loginId,
            @RequestParam(name = "productId") Long productId
    ){

        CartSaveResponse response = cartService.save(loginId, productId);

        return ResponseDto.<CartSaveResponse>builder()
                .success(true)
                .status(HttpStatus.CREATED)
                .data(response)
                .build();

    }


    @PreAuthorize("hasAnyRole({'ROLE_USER', 'ROLE_ADMIN'})")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseDto<Void> delete(
            @LoginId(required = true) String loginId,
            @RequestParam(name = "productId") Long productId){

        cartService.delete(loginId, productId);
        return ResponseDto.<Void>builder()
                .success(true)
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
