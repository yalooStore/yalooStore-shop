package com.yaloostore.shop.member.repository.querydsl.impl;

import com.yaloostore.shop.member.common.GenderCode;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dummy.MembershipDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberLoginHistory;
import com.yaloostore.shop.member.repository.querydsl.inter.QueryMemberLoginHistoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class QueryMemberLoginHistoryRepositoryImplTest {


    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    QueryMemberLoginHistoryRepository queryMemberLoginHistoryRepository;

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
                .isSocialMember(false)
                .build();

        entityManager.persist(member);
    }


    @DisplayName("로그인 시 해당 로그인 기록을 남기는 로직에서 1년간 접속하지 않은 회원 찾아오는 테스트")
    @Test
    void queryFindMemberBySleeper() {

        //given
        LocalDate oneYearsAgo = LocalDate.now().minusYears(1);
        LocalDate start = LocalDate.now();

        memberLoginHistory = MemberLoginHistory.builder()
                .member(member)
                .loginTime(oneYearsAgo)
                .build();
        entityManager.persist(memberLoginHistory);

        //when
        List<MemberIdResponse> memberIdResponses = queryMemberLoginHistoryRepository.queryFindMemberBySleeper(start);

        //then
        assertThat(memberIdResponses.isEmpty()).isFalse();
        assertThat(memberIdResponses.get(0)).isEqualTo(member.getMemberId());

    }

    @DisplayName("로그인 시 해당 로그인 기록을 남기는 로직에서 1년간 접속하지 않은 회원이 없는 경우")
    @Test
    void queryFindMemberBySleeper_notFound() {

        //given
        LocalDate oneDaysAgo = LocalDate.now().minusDays(1);
        LocalDate start = LocalDate.now();

        memberLoginHistory = MemberLoginHistory.builder()
                .member(member)
                .loginTime(oneDaysAgo)
                .build();
        entityManager.persist(memberLoginHistory);

        //when
        List<MemberIdResponse> memberIdResponses = queryMemberLoginHistoryRepository.queryFindMemberBySleeper(start);

        //then
        assertThat(memberIdResponses.isEmpty()).isTrue();

    }
}