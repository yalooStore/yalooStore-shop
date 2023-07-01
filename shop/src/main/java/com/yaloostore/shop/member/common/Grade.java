package com.yaloostore.shop.member.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Grade {

    WHITE(1, "화이트"),
    SILVER(2, "브론즈"),
    GOLD(3, "골드"),
    PLATINUM(4, "플래티넘");

    private final int id;

    private final String name;

}
