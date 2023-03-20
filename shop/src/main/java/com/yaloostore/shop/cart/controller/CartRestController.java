package com.yaloostore.shop.cart.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.cart.dto.response.CartResponseDto;
import com.yaloostore.shop.cart.dto.response.CartSaveResponse;
import com.yaloostore.shop.cart.service.inter.CartService;
import com.yaloostore.shop.cart.service.inter.QuerydslCartService;
import com.yaloostore.shop.common.aspect.annotation.LoginId;
import com.yaloostore.shop.common.dto.PaginationResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service/cart")
public class CartRestController {

    private final CartService cartService;
    private final QuerydslCartService querydslCartService;


    @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_USER'})")
    @PostMapping
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

    /**
     * 장바구니에 해당 상품 등록 유무를 확인하는 컨트롤러입니다.
     * */
    @PreAuthorize("hasAnyRole({'ROLE_USER', 'ROLE_ADMIN'})")
    @GetMapping("/existence")
    public ResponseDto<Boolean> isExist(
            @LoginId(required = true) String loginId,
            @RequestParam(name = "productId") Long productId
    ){

        return ResponseDto.<Boolean>builder()
                .status(HttpStatus.OK)
                .success(true)
                .data(querydslCartService.isExists(loginId,productId))
                .build();

    }


    @PreAuthorize("hasAnyRole({'ROLE_USER', 'ROLE_ADMIN'})")
    @GetMapping
    public ResponseDto<PaginationResponseDto<CartResponseDto>> getCartByMemberLoginId(
            @LoginId(required = true) String loginId,
            @PageableDefault Pageable pageable){


        return ResponseDto.<PaginationResponseDto<CartResponseDto>>builder()
                .status(HttpStatus.OK)
                .success(true)
                .data(querydslCartService.findCartByMemberLoginId(loginId, pageable))
                .build();
    }




}
