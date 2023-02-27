package com.yaloostore.shop.common.open_api.service;

import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ServerException;
import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.book.repository.jpa.JpaBookCommonRepository;
import com.yaloostore.shop.common.open_api.dto.BookChannelResponse_Naver;
import com.yaloostore.shop.common.open_api.dto.BookItemResponse_Naver;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.jpa.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class RestTemplateBookService {


    private final RestTemplate restTemplate;

    private final JpaProductRepository productRepository;
    private final JpaBookCommonRepository bookCommonRepository;

    private Book book;
    private Product product;


    private URI uri;
    private RequestEntity requestEntity;

    @Value("${naver.client.id}")
    private String clientIdNaver;

    @Value("${naver.client.secreyKey}")
    private String clientSecretKeyNaver;


    @Transactional
    public List<BookItemResponse_Naver> getNaverApiDate(String query){

        requestEntity = getRequestEntity(query);

        ResponseEntity<BookChannelResponse_Naver> response = restTemplate.exchange(requestEntity, BookChannelResponse_Naver.class);

        List<BookItemResponse_Naver> items = response.getBody().getItems();

        List<BookItemResponse_Naver> resultItemList = new ArrayList<>();

        for (BookItemResponse_Naver item : items) {
            resultItemList.add(item);
        }


        log.info("resultItemList: {}", resultItemList);
        return resultItemList;
    }


    @Transactional
    public List<BookItemResponse_Naver> saveProductAndBook(String query){

        requestEntity = getRequestEntity(query);

        ResponseEntity<BookChannelResponse_Naver> response = restTemplate.exchange(requestEntity, BookChannelResponse_Naver.class);

        List<BookItemResponse_Naver> items = response.getBody().getItems();
        List<BookItemResponse_Naver> result = new ArrayList<>();

        for (BookItemResponse_Naver item : items) {
            String isbn = item.getIsbn();

            if (bookCommonRepository.existsByIsbn(isbn)){
                throw new ServerException(ErrorCode.PRODUCT_BOOK_ISBN_ALREADY_EXISTS,
                        "this book is already exist");
            }

            Long rawPrice = Math.round(Long.parseLong(item.getDiscount())/ 0.9);

            log.info("item discount price : {}", item.getDiscount());
            log.info("rawPrice: {}", rawPrice);
            product = Product.builder()
                    .productName(item.getTitle())
                    .stock(100L)
                    .productCreatedAt(LocalDateTime.now())
                    .description(item.getDescription())
                    .thumbnailUrl(item.getLink())
                    .fixedPrice(Long.valueOf(item.getDiscount()))
                    .rawPrice(rawPrice)
                    .isSelled(true)
                    .isExpose(true)
                    .isDeleted(false)
                    .discountPercent(10L)
                    .build();
            productRepository.save(product);
            book = Book.builder()
                    .product(product)
                    .isbn(item.getIsbn())
                    .pageCount(0L)
                    .isEbook(false)
                    .ebookUrl("not ready")
                    .publisherName(item.getPublisher())
                    .authorName(item.getAuthor())
                    .build();
            bookCommonRepository.save(book);

            result.add(item);

        }
        return result;
    }

    private RequestEntity getRequestEntity(String query) {
        String BOOK_OPEN_API_REQUEST_BASIC_URL = "https://openapi.naver.com";
        String BOOK_OPEN_API_REQUEST_URL_PATH = "/v1/search/book.json";


        uri = UriComponentsBuilder
                .fromUriString(BOOK_OPEN_API_REQUEST_BASIC_URL)
                .path(BOOK_OPEN_API_REQUEST_URL_PATH)
                .queryParam("query", query)
                .encode()
                .build()
                .toUri();

        requestEntity = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientIdNaver)
                .header("X-Naver-Client-Secret", clientSecretKeyNaver)
                .build();

        return requestEntity;
    }



}
