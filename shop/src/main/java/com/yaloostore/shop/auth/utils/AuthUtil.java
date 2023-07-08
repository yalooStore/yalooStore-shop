package com.yaloostore.shop.auth.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AuthUtil {

    ACCESS_TOKEN("ACCESS_TOKEN"),
    REFRESH_TOKEN("REFRESH_TOKEN"),
    LOGIN_ID("LOGIN_ID"),
    HEADER_UUID("HEADER_UUID"),
    HEADER_EXPIRED_TIME("HEADER_EXPIRED_TIME"),
    JWT("JWT"),
    PRINCIPAL("PRINCIPAL")
    ;
    private String value;


}
