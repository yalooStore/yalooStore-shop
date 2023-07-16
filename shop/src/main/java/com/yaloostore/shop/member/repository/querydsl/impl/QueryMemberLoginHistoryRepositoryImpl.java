package com.yaloostore.shop.member.repository.querydsl.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.entity.MemberLoginHistory;
import com.yaloostore.shop.member.entity.QMemberLoginHistory;
import com.yaloostore.shop.member.repository.querydsl.inter.QueryMemberLoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class QueryMemberLoginHistoryRepositoryImpl implements QueryMemberLoginHistoryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<MemberIdResponse> queryFindMemberBySleeper(LocalDate today) {
        today = LocalDate.now();
        LocalDate start = today.minusYears(1);

        QMemberLoginHistory memberLoginHistory = QMemberLoginHistory.memberLoginHistory;

        return jpaQueryFactory.select(Projections.constructor(MemberIdResponse.class, memberLoginHistory.member.memberId))
                .from(memberLoginHistory)
                .where(memberLoginHistory.loginTime.eq(start).and(memberLoginHistory.member.isSleepAccount.isFalse()))
                .fetch();

    }


}
