package com.yaloostore.shop.common.open_api.dto;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@JacksonXmlRootElement(localName = "rss")
public class NaverBookDetailByIsbn {

    @JacksonXmlProperty(localName = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Channel {
        private String title;
        private String link;
        private String description;
        private String lastBuildDate;
        private int total;
        private int start;
        private int display;
        private Item item;

        // Getters and setters for other fields
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Item {
        private String title;
        private String link;
        private String image;
        private String author;
        private int price;
        private int discount;
        private String publisher;
        private String pubdate;
        private String isbn;
        private String description;
    }
}
