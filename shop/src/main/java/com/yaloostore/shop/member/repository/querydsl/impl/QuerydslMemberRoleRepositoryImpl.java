package com.yaloostore.shop.member.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaloostore.shop.member.entity.MemberRole;
import com.yaloostore.shop.member.entity.QMember;
import com.yaloostore.shop.member.entity.QMemberRole;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRoleRepository;
import com.yaloostore.shop.role.common.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Query dsl을 사용한 레포지토리로 회원 등급 조회와 관련된 로직을 처리합니다.
 * */
@Repository
@RequiredArgsConstructor
public class QuerydslMemberRoleRepositoryImpl implements QuerydslMemberRoleRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> queryFindMemberRoleByLoginId(String loginId) {

        QMember member = QMember.member;
        QMemberRole memberRole = QMemberRole.memberRole;

        List<RoleType> roleTypeList = jpaQueryFactory.select(memberRole.role.roleType)
                .from(memberRole)
                .where(memberRole.member.id.eq(loginId)).fetch();

        List<String> result = roleTypeList.stream()
                .map(RoleType::getRoleName)
                .collect(Collectors.toList());

//        List<String> result = new ArrayList<>();
//
//        for (RoleType roleType : roleTypeList) {
//            result.add(roleType.getRoleName());
//        }

        return result;
    }
}
