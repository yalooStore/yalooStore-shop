package com.yaloostore.shop.member.repository.jpa;


import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.dummy.MembershipDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.Membership;
import com.yaloostore.shop.member.repository.jpa.JpaMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaMemberRepositoryTest {

    @Autowired
    JpaMemberRepository jpaMemberRepository;

    @Autowired
    TestEntityManager testEntityManager;

    private Member member;
    private Membership membership;

    @BeforeEach
    void setUp(){
        member = MemberDummy.dummy();
        membership = MembershipDummy.dummy();

    }

    @DisplayName("회원 저장 테스트")
    @Test
    void testSaveMember(){
        //given
        testEntityManager.persist(membership);

        //when
        Member savedMember = jpaMemberRepository.save(member);

        //then
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getMembership().getGrade()).isEqualTo(membership.getGrade());
    }

    @DisplayName("id로 해당하는 회원 조회 테스트")
    @Test
    void testMemberFindById(){
        //given
        testEntityManager.persist(membership);
        Member savedMember = testEntityManager.persist(member);

        //when
        Optional<Member> optionalMember = jpaMemberRepository.findMemberByMemberId(savedMember.getMemberId());

        //then
        assertThat(optionalMember.isPresent());
        assertThat(optionalMember.get().getName()).isEqualTo(savedMember.getName());

    }

    @DisplayName("모든 회원 조회 테스트")
    @Test
    void testMemberFindAll(){
        //given
        testEntityManager.persist(membership);
        Member savedMember = testEntityManager.persist(member);

        //when
        List<Member> members = jpaMemberRepository.findAll();

        //then
        assertThat(members).isNotNull();
        assertThat(members.size()).isOne();
    }


    /** 회원가입과 관련된 service 레이어에서 사용될 메소드를 검증하기 위한 테스트 */
    @DisplayName("해당 id를 가진 회원이 있는지 조회하는 테스트")
    @Test
    void testExistMemberById() {

        Member savedMember = testEntityManager.persist(member);

        boolean resultTrue = jpaMemberRepository.existsMemberById(savedMember.getId());
        assertThat(resultTrue).isTrue();

        boolean resultFalse = jpaMemberRepository.existsMemberById("test");
        assertThat(resultFalse).isFalse();

    }
    @DisplayName("해당 닉네임으로 가입한 회원이 있는지 조회하는 테스트")
    @Test
    void testExistMemberByNickname() {

        Member savedMember = testEntityManager.persist(member);

        boolean resultTrue = jpaMemberRepository.existsMemberByNickname(savedMember.getNickname());
        assertThat(resultTrue).isTrue();

        boolean resultFalse = jpaMemberRepository.existsMemberByNickname("test");
        assertThat(resultFalse).isFalse();


    }
    @DisplayName("해당 이메일로 가입한 회원이 있는지 조회하는 테스트")
    @Test
    void testExistMemberByEmailAddress() {
        Member savedMember = testEntityManager.persist(member);

        boolean resultTrue = jpaMemberRepository.existsMemberByEmailAddress(savedMember.getEmailAddress());
        assertThat(resultTrue).isTrue();

        boolean resultFalse = jpaMemberRepository.existsMemberByEmailAddress(savedMember.getEmailAddress());
        assertThat(resultFalse).isTrue();

    }

    @DisplayName("해당 닉네임으로 가입한 회원이 있는지 조회하는 테스트")
    @Test
    void testFndMemberByNickname(){

        final String YALOO = "yaloo";

        Member savedMember = testEntityManager.persist(member);
        Optional<Member> optionalMember = jpaMemberRepository.findMemberByNickname(savedMember.getNickname());

        assertThat(optionalMember.isPresent());
        assertThat(optionalMember.get().getNickname()).isEqualTo(YALOO);

    }


}