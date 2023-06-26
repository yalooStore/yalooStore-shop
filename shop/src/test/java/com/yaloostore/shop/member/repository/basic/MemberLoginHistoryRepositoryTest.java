package com.yaloostore.shop.member.repository.basic;

import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberLoginHistory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberLoginHistoryRepositoryTest {


    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    MemberLoginHistoryRepository memberLoginHistoryRepository;

    private Member member;

    private MemberLoginHistory memberLoginHistory;


    @BeforeEach
    void setUp() {
        member = MemberDummy.dummy();
        member.makeNonSleepAccount();
    }

    @DisplayName("회원 로그인 정보 저장 테스트")
    @Test
    void save(){
        //given
        Member persist = testEntityManager.persist(member);
        LocalDate now = LocalDate.now();
        memberLoginHistory = MemberLoginHistory.builder()
                .member(member)
                .loginTime(now)
                .build();

        //when
        MemberLoginHistory save = memberLoginHistoryRepository.save(memberLoginHistory);

        //then
        Assertions.assertThat(save.getMember().getMemberId()).isEqualTo(member.getMemberId());

    }

}