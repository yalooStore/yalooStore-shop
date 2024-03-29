package com.yaloostore.shop.product.service.inter;

import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.product.dto.response.ProductBookNewStockResponse;
import com.yaloostore.shop.product.dto.response.ProductBookResponseDto;
import com.yaloostore.shop.cart.dto.ViewCartDto;
import com.yaloostore.shop.product.dto.response.ProductDetailViewResponse;
import com.yaloostore.shop.product.dto.response.ProductRecentResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface QueryProductService {

    List<ProductBookNewStockResponse> getProductBookByNewOne();

    /**
     * pagination된 데이터를(product와 book) 전체 정보를 넘겨주는 dto를 사용해서 처리해준다.
     * TODO: 이부분은 type code를 넣어서 원하는 타입이 있을 때 해당하는 리스트만 보여주는 식으로도 처리가 가능(대신 respository에선 이를 위한 메소드를 나눠서 작성하고 여기서 통합해서 진행..)
     * */
    PaginationResponseDto<ProductBookResponseDto> getAllByProductBookList(Pageable pageable,Integer typeId);

    List<ViewCartDto> getCartProduct(Map<String, String> cart);

    ProductDetailViewResponse getProductByProductId(Long productId);

    /**
     * 캐시를 사용해서 신작을 넘겨주는 서비스 메서드
     *
     * @param pageable 페이지 정보
     * @return 신작 dto
     * */
    List<ProductRecentResponseDto> findRecentProducts(Pageable pageable);

    List<ProductRecentResponseDto> findRecentProducts_test(Pageable pageable);
}
