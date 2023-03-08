package com.yaloostore.shop.product.service.impl;

import com.yaloostore.shop.book.dummy.BookDummy;
import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.product.dto.response.ProductBookResponseDto;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.dummy.ProductDummy;
import com.yaloostore.shop.product.repository.querydsl.inter.QuerydslProductRepository;
import com.yaloostore.shop.product.service.inter.ProductTypeService;
import com.yaloostore.shop.product.service.inter.QueryProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class QueryProductServiceTest {


    private QueryProductService queryProductService;
    private ProductTypeService productTypeService;
    private QuerydslProductRepository querydslProductRepository;

    @BeforeEach
    void setup(){

        querydslProductRepository = mock(QuerydslProductRepository.class);

        productTypeService = mock(ProductTypeService.class);

        queryProductService = new QueryProductServiceImpl(querydslProductRepository);



    }


    @DisplayName("새로 입고된 상품 조회")
    @Test
    void getProductBookByNewOne() {



    }


    @DisplayName("모든 상품 조회 - type code id 없는 경우")
    @Test
    void getAllByProductBookList() {

        //given
        List<Product> productList = new ArrayList<>();

        Product product = ProductDummy.dummy();
        Book book1 = BookDummy.dummy(product);
        Product product2 = ProductDummy.dummy2();
        Book book2 = BookDummy.dummy2(product);
        Product product3 = ProductDummy.dummyNonProductType();
        Book book3 = BookDummy.dummy3(product);

        productList.add(ProductDummy.dummy(book1));
        productList.add(ProductDummy.dummy2(book2));
        productList.add(ProductDummy.dummyNonProductType(book3));



        Page<Product> page = new PageImpl<>(
                productList,
                PageRequest.of(0,5),
                productList.size()
        );
        Mockito.when(querydslProductRepository.queryFindAllProduct(any())).thenReturn(page);

        //when
        PaginationResponseDto<ProductBookResponseDto> response = queryProductService.getAllByProductBookList(
                PageRequest.of(0,5),
                null
        );

        //then
        assertThat(response.getTotalPage()).isEqualTo(3);
        assertThat(response.getDataList()).isNotNull();
    }
}