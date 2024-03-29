package com.yaloostore.shop.member.repository.querydsl.impl;


import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.QMember;
import com.yaloostore.shop.member.entity.QMemberAddress;
import com.yaloostore.shop.member.entity.QMemberLoginHistory;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

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

    @Override
    public List<MemberIdResponse> queryFindMemberByBirthMonthDay(String laterDays) {

        QMember member = QMember.member;


        return queryFactory.select(Projections.constructor(MemberIdResponse.class, member.memberId))
                .from(member)
                .where(member.birthday.substring(4,5).eq(laterDays.substring(0,1)),
                        member.birthday.substring(6).eq(laterDays.substring(2)),
                        member.isSoftDelete.isFalse())
                .fetch();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public Optional<Member> findMemberByNickname(String nickname) {
        QMember member = QMember.member;

        return Optional.ofNullable(queryFactory
                .selectFrom(member).where(member.nickname.eq(nickname)
                        .and(member.isSoftDelete.isFalse()
                                .and(member.isSleepAccount.isFalse())))
                .fetchFirst());

    }
    /**
     * {@inheritDoc}
     * */
    @Override
    public Optional<Member> findMemberByPhoneNumber(String phoneNumber) {
        QMember member = QMember.member;

        return Optional.ofNullable(queryFactory
                .selectFrom(member).where(member.phoneNumber.eq(phoneNumber)
                        .and(member.isSoftDelete.isFalse()
                                .and(member.isSleepAccount.isFalse())))
                .fetchFirst());
    }
    /**
     * {@inheritDoc}
     * */
    @Override
    public Optional<Member> findMemberByEmail(String email) {
        QMember member = QMember.member;

        return Optional.ofNullable(queryFactory
                .selectFrom(member).where(member.emailAddress.eq(email)
                        .and(member.isSoftDelete.isFalse()
                                .and(member.isSleepAccount.isFalse())))
                .fetchFirst());
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean existMemberByNickname(String nickname) {
        QMember member = QMember.member;

        return Optional.ofNullable(queryFactory
                .selectFrom(member).where(member.nickname.eq(nickname)
                        .and(member.isSoftDelete.isFalse()))
                .fetchFirst()).isPresent();    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean existMemberByPhoneNumber(String phoneNumber) {
        QMember member = QMember.member;

        return Optional.ofNullable(queryFactory
                .selectFrom(member).where(member.phoneNumber.eq(phoneNumber)
                        .and(member.isSoftDelete.isFalse()))
                .fetchFirst()).isPresent();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean existMemberByEmail(String email) {
        QMember member = QMember.member;

        return Optional.ofNullable(queryFactory
                .selectFrom(member).where(member.emailAddress.eq(email)
                        .and(member.isSoftDelete.isFalse()))
                .fetchFirst()).isPresent();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean existMemberByLoginId(String loginId) {
        QMember member = QMember.member;

        return Optional.ofNullable(queryFactory
                .selectFrom(member).where(member.id.eq(loginId)
                        .and(member.isSoftDelete.isFalse())).fetchFirst()).isPresent();
    }


}
