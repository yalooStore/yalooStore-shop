package com.yaloostore.shop.product.service.impl;

import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.product.dto.response.ProductBookNewOneResponse;
import com.yaloostore.shop.product.dto.response.ProductBookResponseDto;
import com.yaloostore.shop.product.dto.response.ProductFindResponse;
import com.yaloostore.shop.product.repository.querydsl.QuerydslProductRepository;
import com.yaloostore.shop.product.service.inter.QueryProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QueryProductServiceImpl implements QueryProductService {


    private final QuerydslProductRepository querydslProductRepository;

    @Override
    public List<ProductBookNewOneResponse> getProductBookByNewOne() {

        List<ProductBookNewOneResponse> response = querydslProductRepository.queryFindProductNewOne();

        if (Objects.isNull(response)) {
            throw new ClientException(ErrorCode.PRODUCT_NOT_FOUND, "this list is empty");
        }

        return response;
    }


    /**
     * {@inheritDoc}
     * */
    @Override
    public PaginationResponseDto<ProductBookResponseDto> getAllByProductBookList(Pageable pageable) {

        Page<ProductFindResponse> page = querydslProductRepository.queryFindAllProductsUser(pageable);

        return null;
    }


}

