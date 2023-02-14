package com.yaloostore.shop.product.product_type.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductType is a Querydsl query type for ProductType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductType extends EntityPathBase<ProductType> {

    private static final long serialVersionUID = -1463824433L;

    public static final QProductType productType = new QProductType("productType");

    public final StringPath productTypeName = createString("productTypeName");

    public final NumberPath<Long> productTypeNo = createNumber("productTypeNo", Long.class);

    public QProductType(String variable) {
        super(ProductType.class, forVariable(variable));
    }

    public QProductType(Path<? extends ProductType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductType(PathMetadata metadata) {
        super(ProductType.class, metadata);
    }

}

