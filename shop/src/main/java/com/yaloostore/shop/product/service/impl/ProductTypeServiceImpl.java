package com.yaloostore.shop.product.service.impl;

import com.yaloostore.shop.product.common.ProductTypeCode;
import com.yaloostore.shop.product.dto.response.ProductTypeResponseDto;
import com.yaloostore.shop.product.service.inter.ProductTypeService;
import lombok.Getter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductTypeServiceImpl implements ProductTypeService {


    /**
     * {@inheritDoc}
     * */
    @Transactional(readOnly = true)
    @Override
    public List<ProductTypeResponseDto> findAllProductTypes() {
        return List.of(ProductTypeCode.values())
                .stream()
                .map(type -> new ProductTypeResponseDto(
                        type.getTypeId(),
                        type.name(),
                        type.getTypeKoName()
                )).collect(Collectors.toList());

    }
}
