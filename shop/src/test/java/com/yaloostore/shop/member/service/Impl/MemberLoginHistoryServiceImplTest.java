package com.yaloostore.shop.member.service.Impl;

import com.yaloostore.shop.member.common.GenderCode;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginHistoryResponse;
import com.yaloostore.shop.member.dummy.MembershipDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberLoginHistory;
import com.yaloostore.shop.member.repository.basic.MemberLoginHistoryRepository;
import com.yaloostore.shop.member.repository.querydsl.impl.QueryMemberLoginHistoryRepositoryImpl;
import com.yaloostore.shop.member.repository.querydsl.inter.QueryMemberLoginHistoryRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.member.service.inter.MemberLoginHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MemberLoginHistoryServiceImplTest {

    //TODO: 해당 서비스 로직 작성하기
    private MemberLoginHistoryService service;

    private QuerydslMemberRepository querydslMemberRepository;
    private MemberLoginHistoryRepository memberLoginHistoryRepository;
    private QueryMemberLoginHistoryRepository queryMemberLoginHistoryRepository;

    private Member member;
    private MemberLoginHistory memberLoginHistory;



    @BeforeEach
    void setUp() {
        querydslMemberRepository = Mockito.mock(QuerydslMemberRepository.class);
        memberLoginHistoryRepository = Mockito.mock(MemberLoginHistoryRepository.class);

        queryMemberLoginHistoryRepository = Mockito.mock(QueryMemberLoginHistoryRepository.class);
        service = new MemberLoginHistoryServiceImpl(querydslMemberRepository, memberLoginHistoryRepository, queryMemberLoginHistoryRepository);

        member = Member.builder()
                .membership(MembershipDummy.dummy())
                .id("existMember")
                .nickname("existMember")
                .name("exist")
                .genderCoder(GenderCode.FEMALE)
                .birthday("19990320")
                .password("password")
                .phoneNumber("01099998888")
                .emailAddress("exist@test.com")
                .memberCreatedAt(LocalDateTime.now())
                .isSoftDelete(false)
                .isSleepAccount(false)
                .build();

    }

    @DisplayName("회원이 존재하고 해당 회원이 로그인하는 경우 로그인 기록을 남기는 테스트")
    @Test
    void addLoginHistory() {
        //given = precondition or setup
        String loginId = member.getId();
        given(querydslMemberRepository.queryFindUndeletedMemberLoginId(loginId))
                .willReturn(Optional.of(member));

        MemberLoginHistory memberLoginHistory = MemberLoginHistory.toEntity(member);

        doReturn(memberLoginHistory).when(memberLoginHistoryRepository).save(memberLoginHistory);
        //when - action or the behavior that we are going test
        MemberLoginHistoryResponse response = service.addLoginHistory(loginId);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getLoginId()).isEqualTo(loginId);
    }

    @DisplayName("접속 기록이 1년이 된 회원을 가져오는 테스트")
    @Test
    void findMemberByLoginHistory() {
        //given
        LocalDate today = LocalDate.now();

        memberLoginHistory = MemberLoginHistory.builder()
                .member(member)
                .loginTime(today.minusYears(1))
                .build();

        doReturn(memberLoginHistory).when(memberLoginHistoryRepository).save(memberLoginHistory);

        List<MemberIdResponse> response = new ArrayList<>();
        doReturn(response).when(queryMemberLoginHistoryRepository).queryFindMemberBySleeper(today);

        //when
        List<MemberIdResponse> memberByLoginHistory = service.findMemberByLoginHistory(today);


        //then
        assertThat(memberByLoginHistory.size()).isOne();

    }

}