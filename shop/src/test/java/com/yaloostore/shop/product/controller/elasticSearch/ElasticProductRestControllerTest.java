package com.yaloostore.shop.product.controller.elasticSearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaloostore.shop.product.documents.SearchProduct;
import com.yaloostore.shop.product.dto.response.SearchProductResponseDto;
import com.yaloostore.shop.product.service.elasticSearch.ElasticProductService;
import jakarta.json.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static com.yaloostore.shop.docs.RestApiDocumentation.getDocumentRequest;
import static com.yaloostore.shop.docs.RestApiDocumentation.getDocumentsResponse;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
@AutoConfigureRestDocs
@WebMvcTest(ElasticProductRestController.class)
class ElasticProductRestControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    ElasticProductService elasticProductService;


    @Autowired
    ObjectMapper objectMapper;

    private SearchProductResponseDto responseDto;
    private SearchProduct searchProduct;

    Pageable pageable = PageRequest.of(0,10);

    @BeforeEach
    void setUp() {


        responseDto = SearchProductResponseDto.builder()
                .productId(1L)
                .productName("test")
                .stock(100L)
                .description("test 설명 주절주절 주절 주절")
                .thumbnailUrl("test url")
                .fixedPrice(1000L)
                .productCreatedAt(LocalDate.of(22,11,4))
                .rawPrice(1100L)
                .isDeleted(false)
                .isExpose(true)
                .isSelled(false)
                .discountPercent(10L)
                .build();

        searchProduct = SearchProduct.builder()
                .productId(1L)
                .productName("test")
                .stock(100L)
                .description("test 설명 주절주절 주절 주절")
                .thumbnailUrl("test url")
                .fixedPrice(1000L)
                .productCreatedAt(LocalDate.of(2022,11,4))
                .rawPrice(1100L)
                .isDeleted(false)
                .isExpose(true)
                .discountPercent(10L)
                .build();



    }


    @DisplayName("상품명 검색 - 성공 (단건 조회만 가능)")
    @WithMockUser
    @Test
    void searchProductByProductNamePagination() throws Exception {

        //given
        Mockito.when(elasticProductService.searchProductByProductName(pageable,"test"))
                .thenReturn(new PageImpl<>(List.of(responseDto), pageable, 2L));

        //when, then
        ResultActions result = mockMvc.perform(get("/api/service/products/search")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("productName", "test"));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.success", equalTo(true)))
                .andExpect(jsonPath("$.errorMessages", equalTo(null)))
                .andExpect(jsonPath("$.data.totalPage", equalTo(1)))
                .andExpect(jsonPath("$.data.currentPage", equalTo(0)))
                .andExpect(jsonPath("$.data.totalDataCount", equalTo(1)))
                .andExpect(jsonPath("$.data.dataList[0].productId", equalTo(responseDto.getProductId().intValue())))
                .andExpect(jsonPath("$.data.dataList[0].productName", equalTo(responseDto.getProductName())))
                .andExpect(jsonPath("$.data.dataList[0].stock", equalTo(responseDto.getStock().intValue())))
                .andExpect(jsonPath("$.data.dataList[0].productCreatedAt", equalTo(responseDto.getProductCreatedAt().toString())))
                .andExpect(jsonPath("$.data.dataList[0].description", equalTo(responseDto.getDescription())))
                .andExpect(jsonPath("$.data.dataList[0].thumbnailUrl", equalTo(responseDto.getThumbnailUrl())))
                .andExpect(jsonPath("$.data.dataList[0].fixedPrice", equalTo(responseDto.getFixedPrice().intValue())))
                .andExpect(jsonPath("$.data.dataList[0].rawPrice", equalTo(responseDto.getRawPrice().intValue())))
                .andExpect(jsonPath("$.data.dataList[0].isSelled", equalTo(responseDto.getIsSelled())))
                .andExpect(jsonPath("$.data.dataList[0].isDeleted", equalTo(responseDto.getIsDeleted())))
                .andExpect(jsonPath("$.data.dataList[0].isExpose", equalTo(responseDto.getIsExpose())))
                .andExpect(jsonPath("$.data.dataList[0].discountPercent", equalTo(responseDto.getDiscountPercent().intValue())));

        //spring REST Docs 문서화 작업
        result.andDo(document(
                "search-product-success-product-name",
                getDocumentRequest(),
                getDocumentsResponse(),
                queryParameters(
                        parameterWithName("productName").description("검색할 상품"),
                        parameterWithName("_csrf").description("csrf")
                ),
                responseFields(
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태"),
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("동작 성공 여부"),
                        //에러 메세지가 성공적으로 동작할 땐 Null값으로 넘어가기 때문에 이에 오류가 나지 않게 하기 위해서 optional을 붙여준다.
                        fieldWithPath("errorMessages").type(JsonFieldType.ARRAY)
                                .description("에러 메세지").optional(),
                        fieldWithPath("data.totalPage").type(JsonFieldType.NUMBER)
                                .description("검색 전체 페이지"),
                        fieldWithPath("data.currentPage").type(JsonFieldType.NUMBER)
                                .description("현재 페이지"),
                        fieldWithPath("data.totalDataCount").type(JsonFieldType.NUMBER)
                                .description("전체 데이터 갯수"),
                        fieldWithPath("data.dataList.[].productId").type(JsonFieldType.NUMBER).description("상품 Id"),
                        fieldWithPath("data.dataList.[].productName").type(JsonFieldType.STRING).description("상품명"),
                        fieldWithPath("data.dataList.[].stock").type(JsonFieldType.NUMBER).description("상품 수량"),
                        fieldWithPath("data.dataList.[].productCreatedAt").type(JsonFieldType.STRING).description("상품 등록시간"),
                        fieldWithPath("data.dataList.[].description").type(JsonFieldType.STRING).description("상품 상세설명"),
                        fieldWithPath("data.dataList.[].thumbnailUrl").type(JsonFieldType.STRING).description("상품 이미지 URL"),
                        fieldWithPath("data.dataList.[].fixedPrice").type(JsonFieldType.NUMBER).description("상품 할인 가격"),
                        fieldWithPath("data.dataList.[].rawPrice").type(JsonFieldType.NUMBER).description("상품 원가격"),
                        fieldWithPath("data.dataList.[].isSelled").type(JsonFieldType.BOOLEAN).description("상품 품절여부"),
                        fieldWithPath("data.dataList.[].isDeleted").type(JsonFieldType.BOOLEAN).description("상품 강제 삭제 여부"),
                        fieldWithPath("data.dataList.[].isExpose").type(JsonFieldType.BOOLEAN).description("상품 노출 여부"),
                        fieldWithPath("data.dataList.[].discountPercent").type(JsonFieldType.NUMBER).description("상품 할인율")
                )
        ));

    }

}