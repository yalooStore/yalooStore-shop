package com.yaloostore.shop.book.repository.common;

import com.yaloostore.shop.book.entity.Book;

public interface CommonBookRepository {


    Book save(Book book);
    boolean existsByIsbn(String isbn);

    Book findBookByIsbn(String isbn);

}
