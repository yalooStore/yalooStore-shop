package com.yaloostore.shop.common.advice.open_api.service;

import com.yaloostore.shop.common.advice.open_api.dto.BookItemResponse_Naver;
import org.apache.coyote.Request;
import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class RestTemplateBookService {


    String BOOK_OPEN_API_REQUEST_BASIC_URL = "https://openapi.naver.com";
    String BOOK_OPEN_API_REQUEST_URL_PATH = "/v1/search/book.json";

    private

    public BookItemResponse_Naver getNaverApiDate(String query){

        URI uri = UriComponentsBuilder
            .fromUriString(BOOK_OPEN_API_REQUEST_BASIC_URL)
            .path(BOOK_OPEN_API_REQUEST_URL_PATH)
            .queryParam("query", query)
            .encode()
            .build()
            .toUri();

        RequestEntity requestEntity = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id: " + )
                .header("X-Naver-Client-Secret: " + )
                .build()


    }
}
