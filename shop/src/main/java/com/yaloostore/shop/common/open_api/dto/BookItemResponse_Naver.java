package com.yaloostore.shop.common.open_api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.nimbusds.jose.shaded.gson.JsonArray;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BookItemResponse_Naver {


    private String title;
    private String link;
    private String image;
    private String author;
    private String price;
    private String discount;
    private String publisher;
    private String isbn;
    private String description;

    public BookItemResponse_Naver(JSONObject itemJson){
        this.title = itemJson.getAsString("title");
        this.link = itemJson.getAsString("link");
        this.image = itemJson.getAsString("image");
        this.author = itemJson.getAsString("author");
        this.price = itemJson.getAsString("price");
        this.discount = itemJson.getAsString("discount");
        this.publisher = itemJson.getAsString("publisher");
        this.isbn = itemJson.getAsString("isbn");
        this.description = itemJson.getAsString("description");
    }



}
