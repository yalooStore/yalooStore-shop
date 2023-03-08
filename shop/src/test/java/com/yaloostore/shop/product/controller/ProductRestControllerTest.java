package com.yaloostore.shop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.product.dto.response.ProductBookNewStockResponse;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.service.inter.QueryProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QueryProductService service;

    private Product product;
    private Book book;
    private List<ProductBookNewStockResponse> newOneResponse;

    @BeforeEach
    void setUp(){


    }


    @DisplayName("스토어에 등록된 시간을 기준으로 도서 10개를 가져오는 메소드 테스트")
    @Test
    @WithMockUser
    void testNewOneLists() throws Exception {
        //given
        Mockito.when(service.getProductBookByNewOne()).thenReturn(newOneResponse);

        //when
        ResultActions perform = mockMvc.perform(get("/api/service/products/new/stock/book")
                .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newOneResponse)));

        //then
        perform.andDo(print()).andExpect(status().isOk());
        verify(service,times(1)).getProductBookByNewOne();
    }

}