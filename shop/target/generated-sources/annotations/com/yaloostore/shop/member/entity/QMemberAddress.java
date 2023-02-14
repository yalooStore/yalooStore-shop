package com.yaloostore.shop.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAddress is a Querydsl query type for MemberAddress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAddress extends EntityPathBase<MemberAddress> {

    private static final long serialVersionUID = 282717103L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAddress memberAddress = new QMemberAddress("memberAddress");

    public final StringPath detailedAddress = createString("detailedAddress");

    public final BooleanPath isDefaultAddress = createBoolean("isDefaultAddress");

    public final BooleanPath isDeletedAddress = createBoolean("isDeletedAddress");

    public final QMember member;

    public final NumberPath<Long> memberAddressId = createNumber("memberAddressId", Long.class);

    public final StringPath recipientName = createString("recipientName");

    public final StringPath recipientPhoneNumber = createString("recipientPhoneNumber");

    public final StringPath streetAddress = createString("streetAddress");

    public final NumberPath<Integer> zipCode = createNumber("zipCode", Integer.class);

    public QMemberAddress(String variable) {
        this(MemberAddress.class, forVariable(variable), INITS);
    }

    public QMemberAddress(Path<? extends MemberAddress> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAddress(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAddress(PathMetadata metadata, PathInits inits) {
        this(MemberAddress.class, metadata, inits);
    }

    public QMemberAddress(Class<? extends MemberAddress> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

