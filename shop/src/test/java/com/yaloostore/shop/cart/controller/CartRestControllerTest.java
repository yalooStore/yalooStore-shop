package com.yaloostore.shop.cart.controller;

import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.cart.dto.response.CartSaveResponse;
import com.yaloostore.shop.cart.service.inter.CartService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//TODO: test 수정할 것(통과 x)

@WebMvcTest(CartRestController.class)
class CartRestControllerTest {

    @Autowired
    MockMvc mockMvc;


    @MockBean
    CartService cartService;

    @DisplayName("장바구니 저장 테스트 - 실패(Member Not Found Exception)")
    @WithMockUser
    @Test
    void save_MemberNotFound() throws Exception {
        Long productId = 1L;


        doThrow(new ClientException(ErrorCode.MEMBER_NOT_FOUND, "member not found"))
                .when(cartService).save(any(), eq(productId));

        ResultActions resultActions = mockMvc.perform(get("/api/service/cart")
                .with(csrf()).queryParam("productId", "1"));

        resultActions.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath(
                        "$.errorMessages[0]",
                        equalTo(ErrorCode.MEMBER_NOT_FOUND.getName())
                ));


    }

    @DisplayName("장바구니 저장 테스트 - 실패(Product Not Found Exception)")
    @WithMockUser
    @Test
    void save_ProductNotFound() throws Exception {
        Long productId = 1L;

        when(cartService.save(any(),eq(productId)))
                .thenThrow(new ClientException(ErrorCode.PRODUCT_NOT_FOUND,
                        "product not found"));

        ResultActions perform = mockMvc.perform(get("/api/service/cart")
                .with(csrf()).queryParam("productId", "1"));

        perform.andExpect(status().isNotFound());

        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath(
                        "$.errorMessages[0]",
                        equalTo(ErrorCode.PRODUCT_NOT_FOUND.getName())
                ));


    }

    @DisplayName("장바구니 저장 테스트 - 성공")
    @WithMockUser
    @Test
    void save_success() throws Exception {
        Long productId = 1L;
        LocalDateTime now = LocalDateTime.now();

        when(cartService.save(any(), eq(productId))).thenReturn(new CartSaveResponse(productId, now));


        ResultActions perform = mockMvc.perform(get("/api/service/cart")
                .with(csrf()).queryParam("productId", "1"));

        perform.andDo(print()).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", equalTo(true)))
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.CREATED.value())));

    }


    @DisplayName("장바구니 삭제 테스트 - 성공")
    @WithMockUser
    @Test
    void delete_success() throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(delete("/api/service/cart").with(csrf()).queryParam("productId", "1"));

        resultActions.andExpect(status().isNoContent());
        verify(cartService, atLeastOnce()).delete(any(), any());
    }

    @DisplayName("장바구니 삭제 테스트 - 실패(해당 회원이 없음)")
    @Test
    void delete_MemberNotFound() throws Exception {

        //given
        doThrow(new ClientException(ErrorCode.MEMBER_NOT_FOUND, "")).when(cartService).delete(any(), any());


        //when
        ResultActions resultActions = mockMvc.perform(delete("/api/service/cart")
                .with(csrf()).queryParam("productId", "1"));


        //then
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.errorMessages[0]", equalTo(ErrorCode.MEMBER_NOT_FOUND.getName())))
                .andDo(print());

    }

    @DisplayName("장바구니 삭제 테스트 - 실패(장바구니에 해당 상품이 없는 경우)")
    @Test
    void delete_NotFoundCart() throws Exception {
        //given
        doThrow(new ClientException(ErrorCode.PRODUCT_NOT_FOUND, ""))
                .when(cartService).delete(anyString(), any());


        //when
        ResultActions resultActions = mockMvc.perform(delete("/api/service/cart")
                .with(csrf()).queryParam("productId", "1"));


        //then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.errorMessages[0]", equalTo(ErrorCode.PRODUCT_NOT_FOUND.getName())))
                .andDo(print());
    }


}