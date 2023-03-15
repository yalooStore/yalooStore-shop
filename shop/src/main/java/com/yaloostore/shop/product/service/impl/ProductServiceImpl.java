package com.yaloostore.shop.product.service.impl;

import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.basic.ProductCommonRepository;
import com.yaloostore.shop.product.service.inter.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductCommonRepository productCommonRepository;

    @Override
    public Product findById(Long productId) {

        Product product = productCommonRepository.findByProductId(productId).orElseThrow(() -> {
            throw new ClientException(ErrorCode.PRODUCT_NOT_FOUND, "this product id is not found");
        });
        return product;
    }
}
