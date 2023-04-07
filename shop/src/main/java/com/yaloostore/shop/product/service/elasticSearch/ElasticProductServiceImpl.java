package com.yaloostore.shop.product.service.elasticSearch;

import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.product.dto.response.SearchProductResponseDto;
import com.yaloostore.shop.product.dto.transfer.SearchProductTransfer;
import com.yaloostore.shop.product.documents.SearchProduct;
import com.yaloostore.shop.product.repository.elasticSearch.ElasticCommonProductRepository;
import com.yaloostore.shop.product.repository.elasticSearch.ElasticProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ElasticProductServiceImpl implements ElasticProductService {

    private final ElasticCommonProductRepository elasticCommonProductRepository;
    private final ElasticProductRepository elasticProductRepository;


    /**
     * {@inheritDoc}
     * */
    @Override
    public Page<SearchProductResponseDto> searchProductByProductName(Pageable pageable, String productName) {
        Page<SearchProduct> searchProducts = elasticProductRepository.searchProductsByProductName(productName, pageable);


        List<SearchProductResponseDto> response = searchProducts.stream()
                .map(SearchProductTransfer::fromDocument).collect(Collectors.toList());

        //cotent pageable, total
        return new PageImpl<>(response, pageable, searchProducts.getTotalElements());
    }


    /**
     * {@inheritDoc}
     * */
    @Override
    public PaginationResponseDto<SearchProductResponseDto> searchProductByProductNamePagination(Pageable pageable, String productName) {

        Page<SearchProduct> page = elasticCommonProductRepository.findByProductName(pageable, productName);


        return getSearchProductPaginationResponse(page);
    }

    private PaginationResponseDto<SearchProductResponseDto> getSearchProductPaginationResponse(Page<SearchProduct> page) {

        List<SearchProductResponseDto> list = new ArrayList<>();

        for (SearchProduct searchProduct : page.getContent()) {
            list.add(SearchProductResponseDto.builder()
                    .productId(searchProduct.getProductId())
                    .productName(searchProduct.getProductName())
                    .stock(searchProduct.getStock())
                    .productCreatedAt(searchProduct.getProductCreatedAt())
                    .description(searchProduct.getDescription())
                    .thumbnailUrl(searchProduct.getThumbnailUrl())
                    .fixedPrice(searchProduct.getFixedPrice())
                    .rawPrice(searchProduct.getRawPrice())
                    .isSelled(searchProduct.getIsSold())
                    .isDeleted(searchProduct.getIsDeleted())
                    .isExpose(searchProduct.getIsExpose())
                    .discountPercent(searchProduct.getDiscountPercent())
                    .build());
        }


        return PaginationResponseDto.<SearchProductResponseDto>builder()
                .totalDataCount(page.getTotalElements())
                .currentPage(page.getNumber())
                .totalPage(page.getTotalPages())
                .dataList(list)
                .build();
    }


}
