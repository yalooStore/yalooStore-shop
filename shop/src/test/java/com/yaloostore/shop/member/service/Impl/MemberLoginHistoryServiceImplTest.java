package com.yaloostore.shop.member.service.Impl;

import com.yaloostore.shop.member.common.GenderCode;
import com.yaloostore.shop.member.dto.response.MemberLoginHistoryResponse;
import com.yaloostore.shop.member.dummy.MembershipDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberLoginHistory;
import com.yaloostore.shop.member.repository.basic.MemberLoginHistoryRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.member.service.inter.MemberLoginHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MemberLoginHistoryServiceImplTest {

    private MemberLoginHistoryService service;

    private QuerydslMemberRepository querydslMemberRepository;
    private MemberLoginHistoryRepository memberLoginHistoryRepository;

    private Member member;


    @BeforeEach
    void setUp() {
        querydslMemberRepository = Mockito.mock(QuerydslMemberRepository.class);
        memberLoginHistoryRepository = Mockito.mock(MemberLoginHistoryRepository.class);

        service = new MemberLoginHistoryServiceImpl(querydslMemberRepository, memberLoginHistoryRepository);

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
        System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getLoginId()).isEqualTo(loginId);
    }
}