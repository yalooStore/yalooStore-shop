
package com.yaloostore.shop.common.open_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@JacksonXmlRootElement(localName = "channel")
public class BookChannelResponse {

    @JacksonXmlProperty(localName = "title")
    private String title;

    @JacksonXmlProperty(localName = "link")
    private String link;

    @JacksonXmlProperty(localName = "description")
    private String description;

    @JacksonXmlProperty(localName = "lastBuildDate")
    private String lastBuildDate;

    @JacksonXmlProperty(localName = "total")
    private String total;

    @JacksonXmlProperty(localName = "item")
    private List<BookPubDateResponse> items;

}
