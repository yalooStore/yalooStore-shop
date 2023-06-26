package com.yaloostore.shop.member.repository.querydsl.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginHistoryResponse;
import com.yaloostore.shop.member.entity.QMemberLoginHistory;
import com.yaloostore.shop.member.repository.querydsl.inter.QueryLoginHistoryRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QueryMemberLoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class QueryLoginHistoryRepositoryImpl implements QueryLoginHistoryRepository {


    private final JPAQueryFactory jpaQueryFactory;
    /**
     * 오늘을 기준으로 지난 1년간 로그인 이력이 없는 경우에 해당하는 회원을 돌려준다.
     * 이때 이미 회원이 휴먼회원으로 지정되지 않은 경우에만 돌려준다.
     * */
    @Override
    public List<MemberLoginHistoryResponse> findSleepAccountByLoginHistory(LocalDate beforeOneYear) {
        QMemberLoginHistory memberLoginHistory = QMemberLoginHistory.memberLoginHistory;

        List<MemberLoginHistoryResponse> list = jpaQueryFactory
                .select(Projections.constructor(MemberLoginHistoryResponse.class, memberLoginHistory.id,
                        memberLoginHistory.member.memberId,
                        memberLoginHistory.member.id,
                        memberLoginHistory.loginTime
                ))
                .from(memberLoginHistory)
                .where(memberLoginHistory.member.isSleepAccount.isFalse()
                        .and(memberLoginHistory.loginTime.before(beforeOneYear))).fetch();
        return list;

    }
}
