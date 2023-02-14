package com.yaloostore.shop.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1112548009L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.yaloostore.shop.book.QBook book;

    public final StringPath description = createString("description");

    public final NumberPath<Long> discountPercent = createNumber("discountPercent", Long.class);

    public final NumberPath<Long> fixedPrice = createNumber("fixedPrice", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final BooleanPath isExpose = createBoolean("isExpose");

    public final BooleanPath isSelled = createBoolean("isSelled");

    public final DateTimePath<java.time.LocalDateTime> productCreatedAt = createDateTime("productCreatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final StringPath productName = createString("productName");

    public final NumberPath<Long> rawPrice = createNumber("rawPrice", Long.class);

    public final NumberPath<Long> stock = createNumber("stock", Long.class);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.yaloostore.shop.book.QBook(forProperty("book"), inits.get("book")) : null;
    }

}

