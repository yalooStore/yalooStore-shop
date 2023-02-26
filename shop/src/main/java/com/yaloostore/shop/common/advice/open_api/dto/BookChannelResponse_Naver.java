package com.yaloostore.shop.common.advice.open_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookChannelResponse_Naver {

    private String title;
    private String link;
    private String description;
    private String lastBuildDate;
    private String total;
    private String start;
    private String display;
    private List<BookItemResponse_Naver> items;

}
