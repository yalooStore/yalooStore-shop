package com.yaloostore.shop.member.repository.querydsl;

import com.yaloostore.shop.member.common.GenderCode;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
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
                .isSleepAccount(false)
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
                .isSleepAccount(false)
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

    @DisplayName("7자리 회원 정보로 가져오는 방식")
    @Test
    void testQueryFindBirthdayMember(){

        //given


        entityManager.persist(existMember);
        String birthday = existMember.getBirthday();

        Member existMember2 = Member.builder()
                .membership(MembershipDummy.dummy())
                .id("d")
                .nickname("d")
                .name("d")
                .genderCoder(GenderCode.MALE)
                .birthday("19990320")
                .password("password")
                .phoneNumber("01055556666")
                .emailAddress("ddd@test.com")
                .memberCreatedAt(LocalDateTime.now())
                .isSoftDelete(false)
                .build();
        entityManager.persist(existMember2);


        //when
        List<Member> members = memberRepository.queryFindBirthdayMember(birthday);

        //then
        assertThat(members.size()).isEqualTo(2);
        assertThat(members.get(0).getBirthday()).isEqualTo(existMember2.getBirthday());



    }


    @DisplayName("생일쿠폰 발급 대상 회원 조회 - 삭제한 회원은 나오지 않게 하기")
    @Test
    void testQueryFindMemberByBirthMonthDay(){

        //given
        entityManager.persist(existMember);

        Member existMember2 = Member.builder()
                .membership(MembershipDummy.dummy())
                .id("d")
                .nickname("d")
                .name("d")
                .genderCoder(GenderCode.MALE)
                .birthday("19990320")
                .password("password")
                .phoneNumber("01055556666")
                .emailAddress("ddd@test.com")
                .memberCreatedAt(LocalDateTime.now())
                .isSoftDelete(false)
                .build();
        Member deleted = Member.builder()
                .membership(MembershipDummy.dummy())
                .id("dee")
                .nickname("ddde")
                .name("dee")
                .genderCoder(GenderCode.MALE)
                .birthday("19990320")
                .password("password")
                .phoneNumber("0000002333")
                .emailAddress("dddddd@test.com")
                .memberCreatedAt(LocalDateTime.now())
                .isSoftDelete(true)
                .build();

        entityManager.persist(deleted);
        entityManager.persist(existMember2);

        //when
        List<MemberIdResponse> members = memberRepository.queryFindMemberByBirthMonthDay("0320");


        //then
        assertThat(members.size()).isEqualTo(2);
        assertThat(members.get(0).getMemberId()).isEqualTo(existMember.getMemberId());
        assertThat(members.get(1).getMemberId()).isEqualTo(existMember2.getMemberId());

    }

    @DisplayName("이메일로 회원을 가져오기 테스트")
    @Test
    void findMemberByEmail(){
        //given
        entityManager.persist(existMember);
        String emailAddress = existMember.getEmailAddress();
        entityManager.persist(deletedMember);

        //when
        Optional<Member> member = memberRepository.findMemberByEmail(emailAddress);
        Optional<Member> deleted = memberRepository.findMemberByEmail(deletedMember.getEmailAddress());


        //then
        assertThat(member.isPresent());
        assertThat(member.get().getEmailAddress()).isEqualTo(emailAddress);

        assertThat(deleted.isEmpty());

    }

    @DisplayName("휴대전화 번호로 회원 가져오기 테스트")
    @Test
    void findMemberByPhoneNumber(){

        //given
        entityManager.persist(existMember);
        String phoneNumber = existMember.getPhoneNumber();
        entityManager.persist(deletedMember);

        //when
        Optional<Member> member = memberRepository.findMemberByPhoneNumber(phoneNumber);
        Optional<Member> deleted = memberRepository.findMemberByPhoneNumber(deletedMember.getPhoneNumber());


        //then
        assertThat(member.isPresent());
        assertThat(member.get().getPhoneNumber()).isEqualTo(phoneNumber);

        assertThat(deleted.isEmpty());

    }

    @DisplayName("회원 닉네임으로 회원 가져오기 테스트")
    @Test
    void findMemberBMyNickname(){
        //given
        entityManager.persist(existMember);
        String nickname = existMember.getNickname();
        entityManager.persist(deletedMember);

        //when
        Optional<Member> member = memberRepository.findMemberByNickname(nickname);
        Optional<Member> deleted = memberRepository.findMemberByNickname(deletedMember.getNickname());


        //then
        assertThat(member.isPresent());
        assertThat(member.get().getNickname()).isEqualTo(nickname);

        assertThat(deleted.isEmpty());
    }
    @DisplayName("회원 휴대전화번호로 회원 가져오기 테스트")
    @Test
    void existMemberByPhoneNumber(){
        //given
        entityManager.persist(existMember);
        String phone = existMember.getPhoneNumber();
        entityManager.persist(deletedMember);

        //when
        boolean result = memberRepository.existMemberByPhoneNumber(phone);
        boolean deletedResult = memberRepository.existMemberByNickname(deletedMember.getPhoneNumber());

        //then
        assertThat(result).isTrue();
        assertThat(deletedResult).isFalse();
    }

    @DisplayName("이메일로 회원 가져오기 테스트")
    @Test
    void existMemberByEmail(){
        //given
        entityManager.persist(existMember);
        String email = existMember.getEmailAddress();
        entityManager.persist(deletedMember);

        //when
        boolean result = memberRepository.existMemberByEmail(email);
        boolean deletedResult = memberRepository.existMemberByEmail(deletedMember.getEmailAddress());

        //then
        assertThat(result).isTrue();
        assertThat(deletedResult).isFalse();
    }
    @DisplayName("회원 닉네임으로 회원 가져오기 테스트")
    @Test
    void existMemberBMyNickname(){
        //given
        entityManager.persist(existMember);
        String nickname = existMember.getNickname();
        entityManager.persist(deletedMember);

        //when
        boolean result = memberRepository.existMemberByNickname(nickname);
        boolean deletedResult = memberRepository.existMemberByNickname(deletedMember.getNickname());

        //then
        assertThat(result).isTrue();
        assertThat(deletedResult).isFalse();
    }

    @DisplayName("회원 로그인 아이디로 회원 가져오기 테스트")
    @Test
    void existMemberBMyLoginId(){
        //given
        entityManager.persist(existMember);
        String loginId = existMember.getId();
        entityManager.persist(deletedMember);

        //when
        boolean result = memberRepository.existMemberByLoginId(loginId);
        boolean deletedResult = memberRepository.existMemberByLoginId(deletedMember.getId());

        //then
        assertThat(result).isTrue();
        assertThat(deletedResult).isFalse();
    }




}