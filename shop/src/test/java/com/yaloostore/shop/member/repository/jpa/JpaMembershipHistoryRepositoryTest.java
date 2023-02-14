package com.yaloostore.shop.member.repository.jpa;


import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.dummy.MembershipDummy;
import com.yaloostore.shop.member.dummy.MembershipHistoryDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.Membership;
import com.yaloostore.shop.member.entity.MembershipHistory;
import com.yaloostore.shop.member.repository.jpa.JpaMembershipHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaMembershipHistoryRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    JpaMembershipHistoryRepository jpaMembershipHistoryRepository;

    private Member member;
    private Membership membership;
    private MembershipHistory membershipHistory;


    @BeforeEach
    void setUp(){
        membership = MembershipDummy.dummy();
    }


    @DisplayName("멤버십 히스토리 저장 테스트")
    @Test
    void testMembershipHistorySave(){
        //given
        Membership savedMembership = testEntityManager.persist(membership);
        member = MemberDummy.dummy(savedMembership);
        Member savedMember = testEntityManager.persist(member);
        membershipHistory = MembershipHistoryDummy.dummy(savedMember, savedMembership);

        //when
        MembershipHistory savedMembershipHistory = jpaMembershipHistoryRepository.save(membershipHistory);

        //then
        assertThat(jpaMembershipHistoryRepository.count()).isOne();
        assertThat(savedMembershipHistory.getMember().getMemberId()).isEqualTo(member.getMemberId());
        assertThat(savedMembershipHistory.getMembership().getMembershipId()).isEqualTo(membership.getMembershipId());

    }

}