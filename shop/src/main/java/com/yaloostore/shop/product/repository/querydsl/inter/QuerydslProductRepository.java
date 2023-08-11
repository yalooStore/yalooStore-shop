package com.yaloostore.shop.product.repository.querydsl.inter;


import com.yaloostore.shop.product.dto.response.ProductFindResponse;

import com.yaloostore.shop.product.dto.response.ProductBookNewStockResponse;
import com.yaloostore.shop.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QuerydslProductRepository {


    Optional<ProductFindResponse> queryFindProductById(Long id);


    Page<ProductFindResponse> queryFindAllProductsAdmin(Pageable pageable);

    Page<ProductFindResponse> queryFindAllProductsUser(Pageable pageable);

    /**
     * 쇼핑몰에 새로 입고된 순서대로(출판 시기대로 x) 새로 입고된 상품을 10개 나열해주는 메소드입니다.
     * 가격이 0원이면 절판된 것으로 간주해 이를 포함하지 않도록하고 노출이 허용된 상품만 가져올 수 있도록 합니다.
     * */
    List<ProductBookNewStockResponse> queryFindProductNewOne();


    /**
     * 모든 상품 정보를 넘겨준다.
     *
     * 입고 날짜를 기준으로 최근에 입고된 순서대로 보여주는 것을 진행(이부분은 나중에 수정)
     * TODO: rank를 두고 판매순 or 인기순으로 변경할수 있게 하자
     * */
    Page<Product> queryFindAllProduct(Pageable pageable);

    Page<Product> queryFindAllProductByProductType(Pageable pageable, Integer typeId);


    Optional<Product> queryFindByProductId(Long productId);


    /**
     * 해당 상품 id와 일치하는 상품 엔티티를 넘겨주는 메소드
     * */
    Optional<Product> queryFindId(Long productId);


    /**
     * 도서 신작을 기준으로 정렬해서 신작을 찾아주는 메소드입니다.
     *
     * @param pageable 페이지 정보
     * @return 신작 리스트
     * */
    Page<Product> findRecentProductsByCreatedAt(Pageable pageable);

    /**
     * 사용자가 최근에 확인한 상품을 돌려주는 작업입니다.
     * */
    Page<Product> findRecentViewProductByProductId(List<Long> totalIds,
                                                          List<Long> pageIds,
                                                          Pageable pageable);
}
