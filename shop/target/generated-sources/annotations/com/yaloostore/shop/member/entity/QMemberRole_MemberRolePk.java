package com.yaloostore.shop.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberRole_MemberRolePk is a Querydsl query type for MemberRolePk
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMemberRole_MemberRolePk extends BeanPath<MemberRole.MemberRolePk> {

    private static final long serialVersionUID = 204796126L;

    public static final QMemberRole_MemberRolePk memberRolePk = new QMemberRole_MemberRolePk("memberRolePk");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    public QMemberRole_MemberRolePk(String variable) {
        super(MemberRole.MemberRolePk.class, forVariable(variable));
    }

    public QMemberRole_MemberRolePk(Path<? extends MemberRole.MemberRolePk> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberRole_MemberRolePk(PathMetadata metadata) {
        super(MemberRole.MemberRolePk.class, metadata);
    }

}

