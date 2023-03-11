package com.yaloostore.shop.product.repository.elasticSearch;


import com.yaloostore.shop.product.documents.SearchProductBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticProductBookRepository extends ElasticsearchRepository<SearchProductBook, Long> {

    Page<SearchProductBook> findByBook_Isbn(Pageable pageable, String isbn);

}
