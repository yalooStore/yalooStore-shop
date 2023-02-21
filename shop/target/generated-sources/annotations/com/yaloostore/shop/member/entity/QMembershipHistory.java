package com.yaloostore.shop.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMembershipHistory is a Querydsl query type for MembershipHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMembershipHistory extends EntityPathBase<MembershipHistory> {

    private static final long serialVersionUID = -503039725L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMembershipHistory membershipHistory = new QMembershipHistory("membershipHistory");

    public final QMember member;

    public final QMembership membership;

    public final NumberPath<Long> membershipHistoryId = createNumber("membershipHistoryId", Long.class);

    public final NumberPath<Long> previousPaidAmount = createNumber("previousPaidAmount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updateTime = createDateTime("updateTime", java.time.LocalDateTime.class);

    public QMembershipHistory(String variable) {
        this(MembershipHistory.class, forVariable(variable), INITS);
    }

    public QMembershipHistory(Path<? extends MembershipHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMembershipHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMembershipHistory(PathMetadata metadata, PathInits inits) {
        this(MembershipHistory.class, metadata, inits);
    }

    public QMembershipHistory(Class<? extends MembershipHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.membership = inits.isInitialized("membership") ? new QMembership(forProperty("membership")) : null;
    }

}

