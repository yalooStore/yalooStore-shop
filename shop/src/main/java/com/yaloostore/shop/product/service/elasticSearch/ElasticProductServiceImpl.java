package com.yaloostore.shop.product.service.elasticSearch;

import com.yaloostore.shop.product.dto.response.SearchProductResponseDto;
import com.yaloostore.shop.product.dto.transfer.SearchProductTransfer;
import com.yaloostore.shop.product.documents.SearchProduct;
import com.yaloostore.shop.product.repository.elasticSearch.common.ElasticCommonProductRepository;
import com.yaloostore.shop.product.repository.elasticSearch.impl.SearchProductRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ElasticProductServiceImpl implements ElasticProductService {

    private final ElasticCommonProductRepository elasticCommonProductRepository;
    private final SearchProductRepositoryImpl searchProductRepositoryImpl;


    /**
     * {@inheritDoc}
     * */
    @Override
    public Page<SearchProductResponseDto> searchProductByProductName(Pageable pageable, String productName) {
        Page<SearchProduct> searchProducts = searchProductRepositoryImpl.searchProductsByProductName(productName, pageable);


        List<SearchProductResponseDto> response = searchProducts.stream()
                .map(SearchProductTransfer::fromDocument).collect(Collectors.toList());

        //cotent pageable, total
        return new PageImpl<>(response, pageable, searchProducts.getTotalElements());
    }



}
