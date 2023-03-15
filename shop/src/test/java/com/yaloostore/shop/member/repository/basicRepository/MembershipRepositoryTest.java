package com.yaloostore.shop.member.repository.basicRepository;


import com.yaloostore.shop.member.dummy.MembershipDummy;
import com.yaloostore.shop.member.entity.Membership;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MembershipRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    MembershipRepository membershipRepository;

    private Membership membership;

    @BeforeEach
    void setUp() {
        membership = MembershipDummy.dummy();
    }

    @DisplayName("멤버십 저장 테스트")
    @Test
    void testMembershipSave(){
        //when
        Membership savedMembership = testEntityManager.persist(membership);

        //then
        assertThat(savedMembership).isNotNull();
        assertThat(savedMembership.getMembershipId()).isEqualTo(membership.getMembershipId());
        assertThat(savedMembership.getMembershipPoint()).isEqualTo(membership.getMembershipPoint());
        assertThat(savedMembership.getGrade()).isEqualTo(membership.getGrade());
    }

    @DisplayName("멤버십 아이디로 해당 회원 삭제 테스트")
    @Test
    void testMembershipDeleteById(){
        //given
        Membership savedMembership = testEntityManager.persist(membership);

        //when
        membershipRepository.deleteById(savedMembership.getMembershipId());
        testEntityManager.flush();

        //then
        assertThat(membershipRepository.count()).isZero();

    }

    @DisplayName("멤버십 아이디로 해당 회원의 등급 찾기 테스트")
    @Test
    void testMembershipFindById(){
        //given
        Membership savedMembership = testEntityManager.persist(membership);

        //when
        Optional<Membership> findMembership = membershipRepository.findById(membership.getMembershipId());

        //then
        assertThat(findMembership).isPresent();
        assertThat(findMembership.get().getGrade()).isEqualTo(savedMembership.getGrade());

    }


}