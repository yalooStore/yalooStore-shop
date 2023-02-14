package com.yaloostore.shop.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1287100549L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final StringPath birthday = createString("birthday");

    public final StringPath emailAddress = createString("emailAddress");

    public final EnumPath<com.yaloostore.shop.member.common.GenderCode> genderCoder = createEnum("genderCoder", com.yaloostore.shop.member.common.GenderCode.class);

    public final StringPath id = createString("id");

    public final BooleanPath isSocialMember = createBoolean("isSocialMember");

    public final BooleanPath isSoftDelete = createBoolean("isSoftDelete");

    public final DateTimePath<java.time.LocalDateTime> memberCreatedAt = createDateTime("memberCreatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final QMembership membership;

    public final DateTimePath<java.time.LocalDateTime> memberSoftDeletedAt = createDateTime("memberSoftDeletedAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.membership = inits.isInitialized("membership") ? new QMembership(forProperty("membership")) : null;
    }

}

