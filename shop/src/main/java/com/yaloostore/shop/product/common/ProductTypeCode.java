package com.yaloostore.shop.product.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 상품유형 코드의 이늄 클래스
 * */
@Getter
@RequiredArgsConstructor
public enum ProductTypeCode {


    NONE(1,"없음"),
    BESTSELLER(2,"베스트셀러"),
    RECOMMEND(3,"추천"),
    POPULAR(4,"인기"),
    NEWONE(5,"신작"),
    DISCOUNT(6,"할인"),
    NEWSTOCK(7,"새로 입고된")
    ;

    private final Integer typeId;
    protected final String typeKoName;





}
