package com.yaloostore.shop.book.repository.jpa;

import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.book.repository.common.CommonBookRepository;
import org.springframework.data.repository.Repository;

public interface JpaBookCommonRepository extends Repository<Book, Long>, CommonBookRepository {


}
