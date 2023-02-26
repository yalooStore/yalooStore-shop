package com.yaloostore.shop.common.advice.open_api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemResponse_Naver {

    private String title;
    String link;
    String image;
    String author;
    String price;
    String discount;
    String publisher;
    String isbn;
    String description;

}
