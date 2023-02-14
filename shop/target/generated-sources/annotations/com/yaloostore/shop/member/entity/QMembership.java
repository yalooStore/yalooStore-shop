package com.yaloostore.shop.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMembership is a Querydsl query type for Membership
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMembership extends EntityPathBase<Membership> {

    private static final long serialVersionUID = 2125703233L;

    public static final QMembership membership = new QMembership("membership");

    public final EnumPath<com.yaloostore.shop.member.common.Grade> grade = createEnum("grade", com.yaloostore.shop.member.common.Grade.class);

    public final NumberPath<Long> membershipId = createNumber("membershipId", Long.class);

    public final NumberPath<Long> membershipPoint = createNumber("membershipPoint", Long.class);

    public final NumberPath<Long> membershipStandardAmount = createNumber("membershipStandardAmount", Long.class);

    public QMembership(String variable) {
        super(Membership.class, forVariable(variable));
    }

    public QMembership(Path<? extends Membership> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMembership(PathMetadata metadata) {
        super(Membership.class, metadata);
    }

}

