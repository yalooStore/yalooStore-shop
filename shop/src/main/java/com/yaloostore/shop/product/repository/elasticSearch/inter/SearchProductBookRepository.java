package com.yaloostore.shop.product.repository.elasticSearch.inter;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.yaloostore.shop.product.documents.SearchProduct;
import com.yaloostore.shop.product.documents.SearchProductBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.stream.Collectors;

public interface SearchProductBookRepository {


    /**
     * 작가이름으로 해당하는 상품을 검색하는 메소드입니다.
     * */
    Page<SearchProductBook> searchProductsByAuthor(String Author, Pageable pageable);


    /**
     * 출판사 이름으로 상품을 검색하는 메소드입니다.
     * */
    Page<SearchProductBook> searchProductsByPublisher(String publisher, Pageable pageable);


    /**
     * full text search 방식으로 term으로 분석 과정을 거쳐 저장합니다.
     * 이떄 검색 시에 대소문자, 단수 복수, 원형 여부와 상관 없이 검색이 가능합니다.
     *
     * @param value 검색하고자 하는 검색값
     * @param field 검색할 필드(index 내의 필드)
     * @param pageable 페이지 정보
     * @return 검색한 상품리스트와 총 갯수
     * */
    Page<SearchProductBook> searchProductByFullTextSearch(
            String value,
            String field,
            Pageable pageable
    );


}
