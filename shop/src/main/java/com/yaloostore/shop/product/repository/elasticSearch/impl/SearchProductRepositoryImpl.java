//package com.yaloostore.shop.product.repository.elasticSearch.impl;
//
//
//import com.yaloostore.shop.product.documents.SearchProduct;
//import com.yaloostore.shop.product.repository.elasticSearch.inter.SearchProductRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.SearchHit;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.query.Criteria;
//import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
//import org.springframework.data.elasticsearch.core.query.Query;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// *
// *
// * */
//@RequiredArgsConstructor
//@Repository
//public class SearchProductRepositoryImpl implements SearchProductRepository {
//
//
//    private final ElasticsearchOperations elasticsearchOperations;
//
//
//
//    @Override
//    public Page<SearchProduct> searchProductsByProductName(String productName, Pageable pageable) {
//        Criteria criteria = Criteria.where("productName").contains(productName);
//
//        Query query = new CriteriaQuery(criteria).setPageable(pageable);
//
//        SearchHits<SearchProduct> search = elasticsearchOperations.search(query, SearchProduct.class);
//
//        List<SearchProduct> list = search.stream().map(SearchHit::getContent).collect(Collectors.toList());
//
//        return new PageImpl<>(list, pageable, search.getTotalHits());
//    }
//
//
//
//}
