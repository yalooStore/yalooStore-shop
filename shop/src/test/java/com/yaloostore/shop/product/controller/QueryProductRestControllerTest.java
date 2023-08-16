package com.yaloostore.shop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.product.common.ProductTypeCode;
import com.yaloostore.shop.product.dto.response.ProductDetailViewResponse;
import com.yaloostore.shop.product.dto.response.ProductRecentResponseDto;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.dummy.ProductDetailViewResponseDummy;
import com.yaloostore.shop.product.service.inter.QueryProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.yaloostore.shop.docs.RestApiDocumentation.getDocumentRequest;
import static com.yaloostore.shop.docs.RestApiDocumentation.getDocumentsResponse;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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

        when(service.getProductByProductId(anyLong())).thenReturn(response);


        //when
        ResultActions result = mockMvc.perform(get("/api/service/products/{productId}", ID)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));


        //then
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

    @DisplayName("신작 가져오기 - 성공")
    @WithMockUser
    @Test
    void testGetRecentProductsByBookCreated() throws Exception {

        //given
        List<Product> products = new ArrayList<>();
        List<ProductRecentResponseDto> dtoList = new ArrayList<>();
        Product product1 = Product
                .builder()
                .productId(1L)
                .productName("testtest")
                .isExpose(true)
                .productTypeCode(ProductTypeCode.NONE)
                .description("ddasdadas")
                .stock(10L)
                .rawPrice(1_000L)
                .isSold(false)
                .thumbnailUrl("dsdadas")
                .discountPercent(10L)
                .discountPrice(1000L)
                .forcedOutOfStock(false)
                .isDeleted(false)
                .productCreatedAt(LocalDateTime.now())
                .build();
        Book book1 = Book.builder()
                .product(product1)
                .isbn("dsadsd")
                .isEbook(false)
                .authorName("test")
                .pageCount(100L)
                .bookCreatedAt(LocalDate.now().minusDays(3))
                .publisherName("publisherName")
                .build();

        Product product2 = Product
                .builder()
                .productId(2L)
                .productName("dd")
                .isExpose(true)
                .productTypeCode(ProductTypeCode.NONE)
                .description("dd")
                .stock(10L)
                .rawPrice(1_000L)
                .isSold(false)
                .thumbnailUrl("d")
                .discountPercent(10L)
                .discountPrice(1000L)
                .isDeleted(false)
                .productCreatedAt(LocalDateTime.now())
                .forcedOutOfStock(false)
                .build();
        Book book2 = Book.builder()
                .product(product2)
                .isbn("dd")
                .isEbook(false)
                .authorName("dd")
                .pageCount(100L)
                .bookCreatedAt(LocalDate.now())
                .publisherName("dd")
                .build();

        product1.setBook(book1);
        product2.setBook(book2);

        products.add(product1);
        products.add(product2);

        for (Product product : products) {
            ProductRecentResponseDto dto = ProductRecentResponseDto.fromEntity(product);
            dtoList.add(dto);
        }

        when(service.findRecentProducts(PageRequest.of(0,10))).thenReturn(dtoList);

        //when
        ResultActions perform = mockMvc.perform(get("/api/service/products/new-arrivals")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));


        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());


        // api 문서 자동화
        perform.andDo(document("find-new-arrivals",
                getDocumentRequest(),
                getDocumentsResponse(),
                queryParameters(
                        parameterWithName("_csrf")
                                .description("csrf")),
                responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                .description("성공 여부"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                .description("상태"),
                        fieldWithPath("errorMessages").type(JsonFieldType.STRING)
                                .description("에러 메시지")
                                .optional(),
                        fieldWithPath("data.[].productId").type(JsonFieldType.NUMBER)
                                .description("상품의 id"),
                        fieldWithPath("data.[].productName").type(JsonFieldType.STRING)
                                .description("상품 제목"),
                        fieldWithPath("data.[].thumbnailUrl").type(JsonFieldType.STRING)
                                .description("상품 썸네일 파일"),
                        fieldWithPath("data.[].stock").type(JsonFieldType.NUMBER)
                                .description("상품 재고량"),
                        fieldWithPath("data.[].discountPrice").type(JsonFieldType.NUMBER)
                                .description("할인 판매가"),
                        fieldWithPath("data.[].rawPrice").type(JsonFieldType.NUMBER)
                                .description("정가"),
                        fieldWithPath("data.[].isSold").type(JsonFieldType.BOOLEAN)
                                .description("품절 여부"),
                        fieldWithPath("data.[].forcedOutOfStock").type(JsonFieldType.BOOLEAN)
                                .description("강제 품절 여부"),
                        fieldWithPath("data.[].isEbook").type(JsonFieldType.BOOLEAN)
                                .description("이북 상품 여부"),
                        fieldWithPath("data.[].authorName").type(JsonFieldType.STRING)
                                .description("저자명"),
                        fieldWithPath("data.[].publisherName").type(JsonFieldType.STRING)
                                .description("상품 출판사")

                )
        ));
    }

}