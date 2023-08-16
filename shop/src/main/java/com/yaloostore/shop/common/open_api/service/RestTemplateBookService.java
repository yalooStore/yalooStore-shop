package com.yaloostore.shop.common.open_api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yalooStore.common_utils.exception.ServerException;
import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.book.repository.basic.BookCommonRepository;
import com.yaloostore.shop.common.open_api.dto.BookChannelResponse;
import com.yaloostore.shop.common.open_api.dto.BookChannelResponse_Naver;
import com.yaloostore.shop.common.open_api.dto.BookItemResponse_Naver;
import com.yaloostore.shop.common.open_api.dto.BookPubDateResponse;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.common.ProductTypeCode;
import com.yaloostore.shop.product.repository.basic.ProductCommonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RestTemplateBookService {

    private final RestTemplate restTemplate;

    private final ProductCommonRepository productRepository;
    private final BookCommonRepository bookCommonRepository;
    private Book book;
    private Product product;


    private URI uri;
    private RequestEntity requestEntity;

    @Value("${naver.client.id}")
    private String clientIdNaver;

    @Value("${naver.client.secreyKey}")
    private String clientSecretKeyNaver;


    @Transactional()
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

        ResponseEntity<BookChannelResponse_Naver> response =
                restTemplate.exchange(requestEntity, BookChannelResponse_Naver.class);

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
                    .thumbnailUrl(item.getImage())
                    .discountPrice(Long.valueOf(item.getDiscount()))
                    .rawPrice(rawPrice)
                    .isSold(true)
                    .isExpose(true)
                    .isDeleted(false)
                    .discountPercent(10L)
                    .productTypeCode(ProductTypeCode.NEWSTOCK)
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

    @Transactional
    public List<BookPubDateResponse> addBookPubDateByIsbn(String isbn) throws JsonProcessingException {
        RequestEntity request = getRequestEntityByIsbn(isbn);

        ResponseEntity<String> xmlResponse = restTemplate.exchange(requestEntity, String.class);

        String XML_DATA = xmlResponse.getBody();
        log.info(XML_DATA);

        XmlMapper xmlMapper = new XmlMapper();
        BookChannelResponse bookChannelResponse = xmlMapper.readValue(XML_DATA, BookChannelResponse.class);

        List<BookPubDateResponse> items = bookChannelResponse.getItems();
        List<BookPubDateResponse> result = new ArrayList<>();
        for (BookPubDateResponse item : items) {
            String ISBN = item.getIsbn();
            Book bookByIsbn = bookCommonRepository.findBookByIsbn(ISBN);
            if (Objects.isNull(bookByIsbn)){
                throw new ClientException(ErrorCode.BOOK_ISBN_NOT_FOUND, "해당 isbn에 해당하는 도서 상품이 존재하지 않습니다.");
            }
            bookByIsbn.setBookPubDate(item.getPubDate());

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



    private RequestEntity getRequestEntityByIsbn(String isbn) {

        String URL= "https://openapi.naver.com";
        String PATH = "/v1/search/book_adv.xml";

        URI url = UriComponentsBuilder
                .fromUriString(URL)
                .path(PATH)
                .queryParam("d_isbn", isbn)
                .build()
                .toUri();

        requestEntity = RequestEntity
                .get(url)
                .header("X-Naver-Client-Id", clientIdNaver)
                .header("X-Naver-Client-Secret", clientSecretKeyNaver)
                .build();

        return requestEntity;
    }


}


