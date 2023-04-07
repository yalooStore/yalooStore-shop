package com.yaloostore.shop.product.repository.elasticSearch.inter;


import com.yaloostore.shop.product.documents.SearchProductBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;



public interface ElasticProductBookRepository extends ElasticsearchRepository<SearchProductBook, Long> {

    Page<SearchProductBook> findByBook_Isbn(Pageable pageable, String isbn);
    Page<SearchProductBook> findByProductName(Pageable pageable, String productName);

}
