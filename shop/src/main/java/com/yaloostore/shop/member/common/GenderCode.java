package com.yaloostore.shop.member.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum GenderCode {

    MALE(1), FEMALE(2);

    private final int genderCode;



}
