package com.yaloostore.shop.common.open_api.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.common.open_api.dto.BookItemResponse_Naver;
import com.yaloostore.shop.common.open_api.dto.BookPubDateResponse;
import com.yaloostore.shop.common.open_api.service.RestTemplateBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/open/api")
@RequiredArgsConstructor
//@CrossOrigin(origins = {"https://openapi.naver.com","http://localhost8081/open/api"})
public class RestTemplateBookRestController {

    private final RestTemplateBookService restTemplateBookService;

    @GetMapping("/naver/{searchQuery}")
    public ResponseEntity<List<BookItemResponse_Naver>> naverBookApi(@PathVariable("searchQuery") String searchQuery){
        List<BookItemResponse_Naver> bookItemResponse = restTemplateBookService.getNaverApiDate(searchQuery);

        return ResponseEntity.ok(bookItemResponse);
    }

    @PostMapping("/naver/{searchQuery}")
    public ResponseEntity<List<BookItemResponse_Naver>> naverBookApiSave(@PathVariable("searchQuery") String searchQuery ){

        List<BookItemResponse_Naver> list = restTemplateBookService.saveProductAndBook(searchQuery);

        return ResponseEntity.ok(list);

    }

    @PostMapping("/naver")
    public ResponseEntity<List<BookPubDateResponse>> getBookCreatedAtByIsbn(@RequestParam String d_isbn) throws JsonProcessingException {
        List<BookPubDateResponse> bookPubDateResponses = restTemplateBookService.addBookPubDateByIsbn(d_isbn);

        return ResponseEntity.ok(bookPubDateResponses);

    }
}
