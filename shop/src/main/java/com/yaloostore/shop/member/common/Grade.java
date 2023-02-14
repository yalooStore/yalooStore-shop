package com.yaloostore.shop.member.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Grade {

    WHITE,
    SILVER,
    GOLD,
    PLATINUM;

    private String grade;
}
