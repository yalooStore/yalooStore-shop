package com.yaloostore.shop.product.repository.elasticSearch.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.yaloostore.shop.product.documents.SearchProduct;
import com.yaloostore.shop.product.documents.SearchProductBook;
import com.yaloostore.shop.product.repository.elasticSearch.inter.SearchProductBookRepository;
import com.yaloostore.shop.product.repository.elasticSearch.inter.SearchProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Repository
public class SearchProductBookRepositoryImpl implements SearchProductBookRepository {

    // query를 받아서 elasticsearch에 요청을 보내는 역할을 한다.
    private final ElasticsearchOperations elasticsearchOperations;

    private final String IS_DELETED = "is_deleted";
    private final String IS_EXPOSE = "is_expose";

    @Override
    public Page<SearchProductBook> searchProductsByAuthor(String Author, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SearchProductBook> searchProductsByPublisher(String publisher, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SearchProductBook> searchProductByFullTextSearch(String value, String field, Pageable pageable) {

        NativeQuery query = getDefaultQuery(value, field, pageable);

        SearchHits<SearchProductBook> search = elasticsearchOperations.search(query, SearchProductBook.class);

        List<SearchProductBook> searchProducts = search.stream().map(product -> product.getContent()).collect(Collectors.toList());


        return new PageImpl<>(searchProducts, pageable, search.getTotalHits());
    }


    /**
     * 찾고자하는 필드의 값이 삭제되지 않았고 노출할수 있는 상품일 경우만 돌려준다.
     * */
    private NativeQuery getDefaultQuery(String value,
                                        String field,
                                        Pageable pageable) {
        return NativeQuery.builder()
                .withFilter(QueryBuilders.bool(v-> v.must(
                        getMatchQuery(field, value),
                        getTermQueryByBoolean(IS_DELETED, false),
                        getTermQueryByBoolean(IS_EXPOSE, true)
                )))
                .withPageable(pageable)
                .build();
    }

    /**
     * term 해당 쿼리의 키워드와 일치하는 것들을 찾아준다.
     * */
    private Query getTermQueryByBoolean(String field, boolean expected) {
        return NativeQuery.builder()
                .withQuery(q -> q.term(t -> t.field(field).value(expected)))
                .getQuery();
    }


    /**
     * term과 마찬가지로 일치하는 것을 찾아주는데 검색 키워드를 analyze 한다는 것이 term과 다르다.
     * analyze한 결과가 하나라도 일치한다면 모든 결과가 document에 포함된다.
     * */
    private Query getMatchQuery(String field, String value) {
        return NativeQuery.builder()
                .withQuery(q -> q.match(v -> v.query(value).field(field)))
                .getQuery();
    }

}
