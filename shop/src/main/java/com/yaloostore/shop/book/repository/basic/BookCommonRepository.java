package com.yaloostore.shop.book.repository.basic;

import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.book.repository.common.CommonBookRepository;
import org.springframework.data.repository.Repository;

public interface BookCommonRepository extends Repository<Book, Long>, CommonBookRepository {


}
