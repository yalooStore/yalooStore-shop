package com.yaloostore.shop.member.repository.querydsl.impl;

import com.yaloostore.shop.member.common.GenderCode;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginHistoryResponse;
import com.yaloostore.shop.member.dummy.MembershipDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberLoginHistory;
import com.yaloostore.shop.member.repository.querydsl.inter.QueryLoginHistoryRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QueryMemberLoginHistoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
class QueryLoginHistoryRepositoryImplTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    QueryLoginHistoryRepository loginHistoryRepository;

    private Member member;

    private MemberLoginHistory memberLoginHistory;


    @BeforeEach
    void setUp() {
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
        entityManager.persist(member);

    }

    @DisplayName("휴먼 회원 가져오기 - 지난 1년간 로그인 기록이 없는 경우")
    @Test
    void queryFindMemberBySleeper() {
        //given
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.now().minusYears(1);
        memberLoginHistory = MemberLoginHistory.builder()
                .member(member)
                .loginTime(date)
                .build();

        entityManager.persist(memberLoginHistory);

        //when
        List<MemberLoginHistoryResponse> sleepAccountByLoginHistory = loginHistoryRepository.findSleepAccountByLoginHistory(now);


        //then
        assertThat(sleepAccountByLoginHistory.isEmpty()).isFalse();
        assertThat(sleepAccountByLoginHistory.get(0).getMemberId()).isEqualTo(member.getMemberId());

    }
}