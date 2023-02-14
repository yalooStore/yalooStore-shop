package com.yaloostore.shop.product.product_type.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductTypeRegistration is a Querydsl query type for ProductTypeRegistration
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductTypeRegistration extends EntityPathBase<ProductTypeRegistration> {

    private static final long serialVersionUID = 163761704L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductTypeRegistration productTypeRegistration = new QProductTypeRegistration("productTypeRegistration");

    public final com.yaloostore.shop.product.entity.QProduct product;

    public final QProductType productType;

    public final QProductTypeRegistration_ProductTypeRegistrationPk productTypeRegistrationPk;

    public QProductTypeRegistration(String variable) {
        this(ProductTypeRegistration.class, forVariable(variable), INITS);
    }

    public QProductTypeRegistration(Path<? extends ProductTypeRegistration> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductTypeRegistration(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductTypeRegistration(PathMetadata metadata, PathInits inits) {
        this(ProductTypeRegistration.class, metadata, inits);
    }

    public QProductTypeRegistration(Class<? extends ProductTypeRegistration> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.yaloostore.shop.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.productType = inits.isInitialized("productType") ? new QProductType(forProperty("productType")) : null;
        this.productTypeRegistrationPk = inits.isInitialized("productTypeRegistrationPk") ? new QProductTypeRegistration_ProductTypeRegistrationPk(forProperty("productTypeRegistrationPk")) : null;
    }

}

