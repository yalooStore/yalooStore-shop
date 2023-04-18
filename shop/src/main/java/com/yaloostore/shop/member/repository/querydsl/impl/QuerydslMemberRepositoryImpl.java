package com.yaloostore.shop.member.repository.querydsl.impl;


import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.QMember;
import com.yaloostore.shop.member.entity.QMemberAddress;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuerydslMemberRepositoryImpl implements QuerydslMemberRepository {

    private final JPAQueryFactory queryFactory;


    /**
     * 멤버 id를 통해서 회원탈퇴를 위해 해당하는 Id가 존재하는지 확인하고, 삭제 여부가 false(삭제되지 않은 상태)인 경우의 회원을 돌려주는 메소드.
     *
     * @param memberId 삭제하고자하는 회원 id
     * @return 해당하는 회원 객체
     * */
    @Override
    public Optional<Member> queryFindUndeletedMember(Long memberId) {

        QMember member = QMember.member;

        return Optional.ofNullable(queryFactory.select(member)
                .from(member)
                .where(member.memberId.eq(memberId)
                        .and(member.isSoftDelete.isFalse())).fetchFirst()
        );
    }


    /**
     * 로그인에 사용한 loginId를 사용해서 해당 유저가 존재하는지 확인하고 삭제되지 않은 경우만 회원을 돌려주는 메소드
     *
     * @param loginId 회원아이디
     * @return 해당 조건을 만족하는 회원객체
     * */
    @Override
    public Optional<Member> queryFindUndeletedMemberLoginId(String loginId) {

        QMember member = QMember.member;

        return Optional.ofNullable(queryFactory
                .select(member)
                .from(member)
                .where(member.id.eq(loginId)
                        .and(member.isSoftDelete.isFalse())).fetchFirst());
    }

    @Override
    public List<Member> queryFindBirthdayMember(String birthday) {

        QMember member = QMember.member;

        return queryFactory.selectFrom(member)
                .where(member.birthday.substring(3,5).eq(birthday.substring(3,5)),
                        member.birthday.substring(6).eq(birthday.substring(6)),
                                member.isSoftDelete.isFalse()).fetch();
    }

}
