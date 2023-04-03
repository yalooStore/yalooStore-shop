package com.yaloostore.shop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaloostore.shop.product.dto.response.ProductDetailViewResponse;
import com.yaloostore.shop.product.repository.dummy.ProductDetailViewResponseDummy;
import com.yaloostore.shop.product.service.inter.QueryProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.yaloostore.shop.docs.RestApiDocumentation.getDocumentRequest;
import static com.yaloostore.shop.docs.RestApiDocumentation.getDocumentsResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(QueryProductRestController.class)
class QueryProductRestControllerTest {


    private final Long ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QueryProductService service;


    @Test
    void getAllCartProduct() {
    }


    @WithMockUser
    @DisplayName("상품 상세 조회 - 성공")
    @Test
    void getProductByProductId_success() throws Exception {

        //given
        ProductDetailViewResponse response = ProductDetailViewResponseDummy.dummy();

        Mockito.when(service.getProductByProductId(anyLong())).thenReturn(response);


        //when
        ResultActions result = mockMvc.perform(get("/api/service/products/{productId}", ID)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(service, times(1)).getProductByProductId(ID);


        // Spring REST Docs
        result.andDo(document("find-detail-product",
                getDocumentRequest(),
                getDocumentsResponse(),
                pathParameters(parameterWithName("productId").description("조회할 상품의 아이디")),
                responseFields(
                        fieldWithPath("success")
                                .type(JsonFieldType.BOOLEAN)
                                .description("동작 성공 여부"),
                        fieldWithPath("status")
                                .type(JsonFieldType.NUMBER)
                                .description("상태"),
                        fieldWithPath("data.productId")
                                .type(JsonFieldType.NUMBER)
                                .description("상품 아이디"),
                        fieldWithPath("data.productName")
                                .type(JsonFieldType.STRING)
                                .description("상품명"),
                        fieldWithPath("data.thumbnail")
                                .type(JsonFieldType.STRING)
                                .description("상품 이미지"),
                        fieldWithPath("data.rawPrice")
                                .type(JsonFieldType.NUMBER)
                                .description("상품 가격"),
                        fieldWithPath("data.discountPrice")
                                .type(JsonFieldType.NUMBER)
                                .description("상품 할인 가격"),
                        fieldWithPath("data.discountPercent")
                                .type(JsonFieldType.NUMBER)
                                .description("상품 할인율"),
                        fieldWithPath("data.isSold")
                                .type(JsonFieldType.BOOLEAN)
                                .description("상품 품절 여부"),
                        fieldWithPath("data.quantity")
                                .type(JsonFieldType.NUMBER)
                                .description("상품 개수"),
                        fieldWithPath("data.description")
                                .type(JsonFieldType.STRING)
                                .description("상품 상세 정보"),
                        fieldWithPath("data.isbn")
                                .type(JsonFieldType.STRING)
                                .description("도서 ISBN"),
                        fieldWithPath("data.pageCnt")
                                .type(JsonFieldType.NUMBER)
                                .description("전체 페이지"),
                        fieldWithPath("data.publisherName")
                                .type(JsonFieldType.STRING)
                                .description("출판사"),
                        fieldWithPath("data.authorName")
                                .type(JsonFieldType.STRING)
                                .description("저자"),
                        fieldWithPath("errorMessages").type(JsonFieldType.ARRAY)
                                .description("에러 메세지")
                                .optional()
                    )
                ));


    }
}