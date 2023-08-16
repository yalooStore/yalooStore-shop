package com.yaloostore.shop.common.open_api.dto;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookPubDateResponse {


    private String title;
    private String link;
    private String image;
    private String author;

    private String price;

    private String discount;
    private String publisher;
    private String  pubDate;
    private String isbn;

    private String description;

}
