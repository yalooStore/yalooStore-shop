package com.yaloostore.shop.product.service.impl;

import com.yaloostore.shop.product.common.ProductTypeCode;
import com.yaloostore.shop.product.dto.response.ProductTypeResponseDto;
import com.yaloostore.shop.product.service.inter.ProductTypeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeServiceTest {


    private ProductTypeService productTypeService;

    @BeforeEach
    void setUp() {

        productTypeService = new ProductTypeServiceImpl();
    }

    @DisplayName("상품 유형 전체 조회 테스트 - 성공")
    @Test
    void findAllProductTypes() {

        List<ProductTypeResponseDto> productTypeResponseDtoList = productTypeService.findAllProductTypes();

        assertThat(productTypeResponseDtoList).hasSize(7);
        assertThat(productTypeResponseDtoList.get(0).getTypeKoName()).isEqualTo(ProductTypeCode.NONE.getTypeKoName());
    }
}