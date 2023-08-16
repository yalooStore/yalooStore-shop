package com.yaloostore.shop.book.service;


import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.book.repository.basic.BookCommonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookCommonRepository repository;

    /**
     * {@inheritDoc}
     * */
    @Transactional
    @Override
    public void updateBookCreatedAtByIsbn(String isbn, String changeDate) {

        Book bookByIsbn = repository.findBookByIsbn(isbn);

        if (Objects.isNull(bookByIsbn)){
            throw new ClientException(ErrorCode.BOOK_ISBN_NOT_FOUND,
                    "입력하신 isbn에 해당하는 도서 상품이 없습니다.");
        }
        bookByIsbn.setBookPubDate(changeDate);

    }
}
