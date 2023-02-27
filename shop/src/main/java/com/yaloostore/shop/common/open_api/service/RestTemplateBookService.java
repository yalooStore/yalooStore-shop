package com.yaloostore.shop.common.open_api.service;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.yaloostore.shop.common.open_api.dto.BookChannelResponse_Naver;
import com.yaloostore.shop.common.open_api.dto.BookItemResponse_Naver;
import jakarta.websocket.OnClose;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class RestTemplateBookService {


    private final RestTemplate restTemplate;

    private URI uri;
    private RequestEntity requestEntity;

    @Value("${naver.client.id}")
    private String clientIdNaver;

    @Value("${naver.client.secreyKey}")
    private String clientSecretKeyNaver;


    @Transactional
    public List<BookItemResponse_Naver> getNaverApiDate(String query){

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

        ResponseEntity<BookChannelResponse_Naver> response = restTemplate.exchange(requestEntity, BookChannelResponse_Naver.class);

        List<BookItemResponse_Naver> items = response.getBody().getItems();

        List<BookItemResponse_Naver> resultItemList = new ArrayList<>();

        for (BookItemResponse_Naver item : items) {
            resultItemList.add(item);
        }

        return resultItemList;
    }





}
