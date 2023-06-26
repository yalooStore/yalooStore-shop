package com.yaloostore.shop.member.service.Impl;

import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberLoginHistory;
import com.yaloostore.shop.member.repository.basic.MemberLoginHistoryRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.member.service.inter.QueryLoginHistoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cglib.core.Local;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Transactional(readOnly = true)
class QueryLoginHistoryServiceImplTest {

    private MemberLoginHistoryRepository memberLoginHistoryRepository;
    private QuerydslMemberRepository querydslMemberRepository;
    private QueryLoginHistoryService service;
    private Member member;
    private MemberLoginHistory memberLoginHistory;

    @BeforeEach
    void setUp(){
        memberLoginHistoryRepository  = Mockito.mock(MemberLoginHistoryRepository.class);
        querydslMemberRepository = Mockito.mock(QuerydslMemberRepository.class);

        service = new QueryLoginHistoryServiceImpl(memberLoginHistoryRepository, querydslMemberRepository);

        member = Mockito.mock(Member.class);


    }

    @DisplayName("회원 로그인 이력 추가 서비스 메소드 테스트")
    @Test
    void addLoginHistory(){
        //given
        String loginId = member.getId();
        LocalDate now = LocalDate.now();
        when(querydslMemberRepository.queryFindUndeletedMemberLoginId(loginId))
                .thenReturn(Optional.ofNullable(member));

        memberLoginHistory = MemberLoginHistory.builder()
                .member(member)
                .loginTime(now)
                .build();

        when(memberLoginHistoryRepository.save(memberLoginHistory)).thenReturn(memberLoginHistory);

        //when
        MemberLoginHistory result = service.addLoginHistory(loginId, now);

        //then
        Assertions.assertThat(result.getLoginTime()).isEqualTo(now);

    }
}