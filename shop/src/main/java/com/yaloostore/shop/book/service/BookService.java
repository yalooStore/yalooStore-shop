package com.yaloostore.shop.book.service;

public interface BookService {


    /**
     * isbn으로 찾아온 도서 상품의 출판일을 설정합니다.
     *
     * @param isbn 출판일을 설정해줄 도서의 isbn
     * */
    void updateBookCreatedAtByIsbn(String isbn, String changeDate);


}
