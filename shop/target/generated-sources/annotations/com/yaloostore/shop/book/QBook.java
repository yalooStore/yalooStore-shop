package com.yaloostore.shop.book;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = -359605830L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBook book = new QBook("book");

    public final StringPath authorName = createString("authorName");

    public final DateTimePath<java.time.LocalDateTime> bookCreatedAt = createDateTime("bookCreatedAt", java.time.LocalDateTime.class);

    public final StringPath ebookUrl = createString("ebookUrl");

    public final StringPath isbn = createString("isbn");

    public final BooleanPath isEbook = createBoolean("isEbook");

    public final NumberPath<Long> pageCount = createNumber("pageCount", Long.class);

    public final com.yaloostore.shop.product.entity.QProduct product;

    public final NumberPath<Long> productNo = createNumber("productNo", Long.class);

    public final StringPath publisherName = createString("publisherName");

    public QBook(String variable) {
        this(Book.class, forVariable(variable), INITS);
    }

    public QBook(Path<? extends Book> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBook(PathMetadata metadata, PathInits inits) {
        this(Book.class, metadata, inits);
    }

    public QBook(Class<? extends Book> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.yaloostore.shop.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

