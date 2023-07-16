//package com.yaloostore.shop.product.service.elasticSearch;
//
//
//import com.yaloostore.shop.common.dto.PaginationResponseDto;
//import com.yaloostore.shop.product.dto.response.SearchProductBookResponseDto;
//import com.yaloostore.shop.product.documents.SearchProductBook;
//import com.yaloostore.shop.product.repository.elasticSearch.inter.ElasticProductBookRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class ElasticProductBookServiceImpl implements ElasticProductBookService{
//
//
//    private final ElasticProductBookRepository elasticProductBookRepository;
//
//
//    @Override
//    public PaginationResponseDto<SearchProductBookResponseDto> searchProductBookByIsbn(Pageable pageable, String isbn) {
//
//        Page<SearchProductBook> page = elasticProductBookRepository.findByBook_Isbn(pageable, isbn);
//
//        return getPaginationResponseDto(page);
//    }
//
//    private PaginationResponseDto<SearchProductBookResponseDto> getPaginationResponseDto(Page<SearchProductBook> page){
//        List<SearchProductBookResponseDto> list = new ArrayList<>();
//
//        for (SearchProductBook searchProductBook : page.getContent()) {
//            list.add(SearchProductBookResponseDto.builder()
//                    .productId(searchProductBook.getProductId())
//                    .productName(searchProductBook.getProductName())
//                    .stock(searchProductBook.getStock())
//                    .productCreatedAt(searchProductBook.getProductCreatedAt())
//                    .description(searchProductBook.getDescription())
//                    .thumbnailUrl(searchProductBook.getThumbnailUrl())
//                    .fixedPrice(searchProductBook.getFixedPrice())
//                    .rawPrice(searchProductBook.getRawPrice())
//                    .isSelled(searchProductBook.getIsSelled())
//                    .isDeleted(searchProductBook.getIsDeleted())
//                    .isExpose(searchProductBook.getIsExpose())
//                    .discountPercent(searchProductBook.getDiscountPercent())
//                    .isbn(searchProductBook.getBook().getIsbn())
//                    .pageCount(searchProductBook.getBook().getPageCount())
//                    .bookCreatedAt(searchProductBook.getBook().getBookCreatedAt())
//                    .isEbook(searchProductBook.getBook().getIsEbook())
//                    .ebookUrl(searchProductBook.getBook().getEbookUrl())
//                    .publisherName(searchProductBook.getBook().getPublisherName())
//                    .authorName(searchProductBook.getBook().getAuthorName())
//                    .build());
//        }
//
//        return PaginationResponseDto.<SearchProductBookResponseDto>builder()
//                .totalDataCount(page.getTotalElements())
//                .currentPage(page.getNumber())
//                .totalPage(page.getTotalPages())
//                .dataList(list)
//                .build();
//    }
//}
