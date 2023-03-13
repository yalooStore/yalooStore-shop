package com.yaloostore.shop.product.service.elasticSearch;

import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.product.documents.SearchProduct;
import com.yaloostore.shop.product.dto.transfer.SearchProductTransfer;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.elasticSearch.ElasticCommonProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.yaloostore.shop.product.dto.transfer.SearchProductTransfer.fromDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ElasticProductServiceImpl.class)
class ElasticProductServiceImplTest {

    @Autowired
    ElasticProductService elasticProductService;

    @MockBean
    ElasticCommonProductRepository mockElasticsearchRepository;

    private Product product;

    private SearchProduct searchProduct;

    private PageRequest pageRequest = PageRequest.of(0,10);


    @BeforeEach
    void setup(){
        product = Product.builder()
                .productName("test")
                .stock(100L)
                .description("test 설명 주절주절 주절 주절")
                .thumbnailUrl("test url")
                .fixedPrice(1000L)
                .productCreatedAt(LocalDateTime.of(22,11,4,00,00))
                .rawPrice(1100L)
                .isSelled(false)
                .isDeleted(false)
                .isExpose(true)
                .discountPercent(10L)
                .build();

        Long productId = product.getProductId();

        searchProduct = SearchProduct.builder()
                .productId(productId)
                .productName("test")
                .stock(100L)
                .description("test 설명 주절주절 주절 주절")
                .thumbnailUrl("test url")
                .fixedPrice(1000L)
                .productCreatedAt(LocalDate.of(22,11,4))
                .rawPrice(1100L)
                .isSelled(false)
                .isDeleted(false)
                .isExpose(true)
                .discountPercent(10L)
                .build();
    }

    @DisplayName("상품 이름으로 검색 - @return Page")
    @Test
    void testSearchProductByProductName(){

        //given
        Page page = new PageImpl(List.of(searchProduct, searchProduct), pageRequest, 10);

        given(mockElasticsearchRepository.findByProductName(any(), anyString())).willReturn(page);

        //when
        Page<SearchProduct> result = mockElasticsearchRepository.findByProductName(Pageable.unpaged(), "test");

        //when, then
        assertThat(elasticProductService.searchProductByProductName(Pageable.unpaged(),"test").getContent())
                .usingRecursiveComparison().isEqualTo(List.of(fromDocument(searchProduct),fromDocument(searchProduct)));

        assertThat(result.getContent().get(0).getProductId()).isEqualTo(searchProduct.getProductId());
    }

    @DisplayName("상품 이름으로 검색 테스트 - @return paginationResponseDto")
    @Test
    void testSearchProductByProductNamePagination(){

        //given
        Page page = new PageImpl(List.of(searchProduct, searchProduct), pageRequest, 10);

        given(mockElasticsearchRepository.findByProductName(any(), anyString())).willReturn(page);

        assertThat(elasticProductService.searchProductByProductNamePagination(Pageable.unpaged(), "test").getDataList())
                .usingRecursiveComparison().isEqualTo(List.of(fromDocument(searchProduct), fromDocument(searchProduct)));

    }



}