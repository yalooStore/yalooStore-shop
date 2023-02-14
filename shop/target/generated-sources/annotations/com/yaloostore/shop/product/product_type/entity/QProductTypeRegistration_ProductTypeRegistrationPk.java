package com.yaloostore.shop.product.product_type.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductTypeRegistration_ProductTypeRegistrationPk is a Querydsl query type for ProductTypeRegistrationPk
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProductTypeRegistration_ProductTypeRegistrationPk extends BeanPath<ProductTypeRegistration.ProductTypeRegistrationPk> {

    private static final long serialVersionUID = 749526359L;

    public static final QProductTypeRegistration_ProductTypeRegistrationPk productTypeRegistrationPk = new QProductTypeRegistration_ProductTypeRegistrationPk("productTypeRegistrationPk");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> productTypeNo = createNumber("productTypeNo", Long.class);

    public QProductTypeRegistration_ProductTypeRegistrationPk(String variable) {
        super(ProductTypeRegistration.ProductTypeRegistrationPk.class, forVariable(variable));
    }

    public QProductTypeRegistration_ProductTypeRegistrationPk(Path<? extends ProductTypeRegistration.ProductTypeRegistrationPk> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductTypeRegistration_ProductTypeRegistrationPk(PathMetadata metadata) {
        super(ProductTypeRegistration.ProductTypeRegistrationPk.class, metadata);
    }

}

