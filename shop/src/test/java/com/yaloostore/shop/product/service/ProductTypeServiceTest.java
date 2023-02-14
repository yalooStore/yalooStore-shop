package com.yaloostore.shop.product.service;

import com.yaloostore.shop.product.product_type.dummy.ProductTypeDummy;
import com.yaloostore.shop.product.product_type.entity.ProductType;
import com.yaloostore.shop.product.product_type.service.ProductTypeService;
import com.yaloostore.shop.product.repository.jpa.JpaProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

//TODO: test 완료할 것
/**
 * junit5를 사용한 서비스 레이어 단위 테스트를 위한 테스트 클래스입니다.
 * */

@ExtendWith(MockitoExtension.class)
class ProductTypeServiceTest {

    @Mock
    private JpaProductRepository productRepository;

    @InjectMocks
    private ProductTypeService productTypeService;


    private ProductType productType;

    @BeforeEach
    void setUp(){
        productType = ProductTypeDummy.dummy();
    }

    @Test
    void testGetAllProductTypeList() {

        //given

        //when
        List<ProductType> productTypeList = productTypeService.getAllProductTypeList();

        //then


    }

    @Test
    void testGetProductType() {
    }
}