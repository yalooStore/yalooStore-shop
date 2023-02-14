package com.yaloostore.shop.member.repository.querydsl;

import com.yaloostore.shop.member.common.GenderCode;
import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.dummy.MembershipDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.Membership;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class QuerydslMemberRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    QuerydslMemberRepository memberRepository;
    private Member existMember;
    private Member deletedMember;

    @BeforeEach
    void setUp() {
         existMember = Member.builder()
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
                .build();
        entityManager.persist(existMember);

        deletedMember =Member.builder()
                .membership(MembershipDummy.dummy())
                .id("notExistMember")
                .nickname("notExistMember")
                .name("NotExist")
                .genderCoder(GenderCode.MALE)
                .birthday("19670106")
                .password("deleted")
                .phoneNumber("01066667777")
                .emailAddress("NotExist@test.com")
                .memberCreatedAt(LocalDateTime.now())
                .isSoftDelete(true)
                .build();
        entityManager.persist(deletedMember);

    }


    @DisplayName("query dsl로 회원 찾기 - 찾고자하는 id에 해당하는 회원이 존재하고 삭제되지 않은 상태의 회원만 가져오는 테스트")
    @Test
    void testQueryFindUndeletedMember_Success() {
        //given
        Long memberId = existMember.getMemberId();

        //when
        Optional<Member> member = memberRepository.queryFindUndeletedMember(memberId);

        //then
        assertThat(member.isPresent());
        assertThat(member.get().getId()).isEqualTo("existMember");
        assertThat(member.get().getMemberId()).isSameAs(memberId);
    }

    @DisplayName("query dsl로 회원 찾기 - 찾고자하는 id에 해당하는 회원이 존재하지만 삭제된 상태의 회원으로 가져오기 실패 테스트")
    @Test
    void testQueryFindUndeletedMember_fail() {
        //given
        Long memberId = deletedMember.getMemberId();

        //when
        Optional<Member> member = memberRepository.queryFindUndeletedMember(memberId);

        //then
        assertThat(member.isPresent()).isFalse();
        assertThat(member.isEmpty());
    }

    @DisplayName("query dsl로 회원 찾기 - 찾고자하는 loginId에 해당하는 회원이 존재하고 삭제되지 않은 상태의 회원만 가져오는 테스트")
    @Test
    void testQueryFindUndeletedMemberLoginId_Success() {
        //given
        String loginId = existMember.getId();

        //when
        Optional<Member> member = memberRepository.queryFindUndeletedMemberLoginId(loginId);

        //then
        assertThat(member.isPresent());
        assertThat(member.get().getId()).isEqualTo("existMember");
    }

    @DisplayName("query dsl로 회원 찾기 - 찾고자하는 loginId에 해당하는 회원이 존재하지만 삭제된 상태의 회원으로 가져오기 실패 테스트")
    @Test
    void testQueryFindUndeletedMemberLoginId_fail() {
        //given
        String loginId = deletedMember.getId();

        //when
        Optional<Member> member = memberRepository.queryFindUndeletedMemberLoginId(loginId);

        //then
        assertThat(member.isPresent()).isFalse();
        assertThat(member.isEmpty());
    }
}