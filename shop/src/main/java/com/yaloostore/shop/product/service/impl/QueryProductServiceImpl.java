package com.yaloostore.shop.product.service.impl;

import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.product.dto.response.ProductBookNewStockResponse;
import com.yaloostore.shop.product.dto.response.ProductBookResponseDto;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.querydsl.inter.QuerydslProductRepository;
import com.yaloostore.shop.product.service.inter.QueryProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QueryProductServiceImpl implements QueryProductService {

    private final QuerydslProductRepository querydslProductRepository;

    @Override
    public List<ProductBookNewStockResponse> getProductBookByNewOne() {

        List<ProductBookNewStockResponse> response = querydslProductRepository.queryFindProductNewOne();

        if (Objects.isNull(response)) {
            throw new ClientException(ErrorCode.PRODUCT_NOT_FOUND, "this list is empty");
        }

        return response;
    }


    /**
     * {@inheritDoc}
     * */
    @Override
    public PaginationResponseDto<ProductBookResponseDto> getAllByProductBookList(Pageable pageable,
                                                                                 Integer typeId) {

        Page<Product> page;

        if(Objects.isNull(typeId)){
            page = querydslProductRepository.queryFindAllProduct(pageable);
        } else {
            page = querydslProductRepository.queryFindAllProductByProductType(pageable, typeId);
        }

        return getProductPaginationResponse(page);
    }



    /**
     * 전체 조회한 Page 객체로 전체 조회 화면에 내보낼 정보를 entity가 아닌 dto 객체로 반환
     *
     * @param page 페이징 전체 조회된 객체
     * @return PaginationResponseDto
     * */
    private PaginationResponseDto<ProductBookResponseDto> getProductPaginationResponse(Page<Product> page) {

        List<ProductBookResponseDto> products = new ArrayList<>();

        for (Product product : page.getContent()) {
            products.add(ProductBookResponseDto
                    .builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .stock(product.getStock())
                        .productCreatedAt(product.getProductCreatedAt())
                        .description(product.getDescription())
                        .thumbnailUrl(product.getThumbnailUrl())
                        .fixedPrice(product.getDiscountPrice())
                        .rawPrice(product.getRawPrice())
                        .isSelled(product.getIsSelled())
                        .isDeleted(product.getIsDeleted())
                        .isExpose(product.getIsExpose())
                        .discountPercent(product.getDiscountPercent())
                        .publisherName(product.getBook().getPublisherName())
                        .authorName(product.getBook().getAuthorName())
                        .isbn(product.getBook().getIsbn())
                        .pageCount(product.getBook().getPageCount())
                        .bookCreatedAt(product.getBook().getBookCreatedAt())
                    .build());
        }

        /**
         * List로 추가된 dto 데이터들을 paginationResponseDto data 부근으로 넘겨준다.
         * */
        return PaginationResponseDto.<ProductBookResponseDto>builder()
                .totalDataCount(page.getTotalElements())
                .currentPage(page.getNumber())
                .totalPage(page.getTotalPages())
                .dataList(products)
                .build();
    }


}

