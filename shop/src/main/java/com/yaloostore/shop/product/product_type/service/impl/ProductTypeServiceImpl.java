package com.yaloostore.shop.product.product_type.service.impl;

import com.yaloostore.shop.product.exception.NotExistProductType;
import com.yaloostore.shop.product.exception.NotFoundProductTypeException;
import com.yaloostore.shop.product.product_type.entity.ProductType;
import com.yaloostore.shop.product.product_type.repository.jpa.JpaProductTypeRepository;
import com.yaloostore.shop.product.product_type.service.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductTypeServiceImpl implements ProductTypeService {

    private final JpaProductTypeRepository jpaProductTypeRepository;

    @Override
    public List<ProductType> getAllProductTypeList() {

        List<ProductType> productTypeList = jpaProductTypeRepository.findAll();
        if(Objects.isNull(productTypeList)){
            throw new NotExistProductType();
        }

        return productTypeList;
    }

    @Override
    public Optional<ProductType> getProductType(Long productTypeNo) {
        return Optional.ofNullable(jpaProductTypeRepository.findByProductTypeNo(productTypeNo).orElseThrow(NotFoundProductTypeException::new));
    }


}
