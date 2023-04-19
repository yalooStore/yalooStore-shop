package com.yaloostore.shop.member.service;

import com.yaloostore.shop.member.common.GenderCode;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberSoftDeleteResponse;
import com.yaloostore.shop.member.dummy.MembershipDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.exception.NotFoundMemberException;
import com.yaloostore.shop.member.repository.basic.MemberRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRoleRepository;
import com.yaloostore.shop.member.service.Impl.QueryMemberServiceImpl;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@Transactional(readOnly = true)
class QueryMemberServiceTest {

    private QueryMemberService service;

    private QuerydslMemberRepository queryMemberRepository;

    private QuerydslMemberRoleRepository querydslMemberRoleRepository;
    private MemberRepository memberRepository;

    private Member member;

    private Member existMember;
    private Member deletedMember;


    @BeforeEach
    void setUp() {
        memberRepository = Mockito.mock(MemberRepository.class);
        queryMemberRepository = Mockito.mock(QuerydslMemberRepository.class);
        querydslMemberRoleRepository = Mockito.mock(QuerydslMemberRoleRepository.class);
        member = Mockito.mock(Member.class);

        service = new QueryMemberServiceImpl(queryMemberRepository, querydslMemberRoleRepository);

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
        memberRepository.save(existMember);

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
        memberRepository.save(deletedMember);
    }


    @Transactional
    @DisplayName("서비스 계층의 회원 삭제 테스트 - 찾는 회원이 없을 때, notFoundMember 예외던지는 경우")
    @Test
    void testSoftDeleteMember_NotFoundMember() {
        //given
        Long memberId = member.getMemberId();

        when(queryMemberRepository.queryFindUndeletedMember(memberId)).thenReturn(Optional.empty());

        //when, then
        assertThatThrownBy(()-> service.softDeleteMember(memberId)).isInstanceOf(NotFoundMemberException.class);
    }

    @Transactional
    @DisplayName("서비스 계층의 회원 삭제 테스트 - 찾는 회원은 있는데 이미 삭제된 경우 notFoundMember 예외를 던진다.")
    @Test
    void testSoftDeleteMember_AlreadyDeletedMember() {
        //given
        String loginId = deletedMember.getId();

        when(queryMemberRepository.queryFindUndeletedMemberLoginId(loginId)).thenReturn(Optional.empty());

        //when, then
        assertThatThrownBy(()-> service.softDeleteLoginId(loginId)).isInstanceOf(NotFoundMemberException.class);
    }

    @Transactional
    @DisplayName("서비스 계층의 회원 삭제 테스트 - 성공")
    @Test
    void testSoftDeleteMember_Success(){
        //given
        String loginId = existMember.getId();
        when(queryMemberRepository.queryFindUndeletedMemberLoginId(loginId)).thenReturn(Optional.ofNullable(existMember));

        //when
        MemberSoftDeleteResponse response = service.softDeleteLoginId(loginId);

        //then
        assertThat(response.isSoftDelete()).isTrue();

    }

    @Transactional
    @DisplayName("해당 날짜에 생일인 회원 찾기 - 성공")
    @Test
    void testFindMemberByBirthday(){
        //given
        String birthday = existMember.getBirthday();

        Member existMember2 = Member.builder()
                .membership(MembershipDummy.dummy())
                .id("w")
                .nickname("ee")
                .name("222")
                .genderCoder(GenderCode.FEMALE)
                .birthday("20230426")
                .password("312")
                .phoneNumber("3213213213231")
                .emailAddress("ddd@test.com")
                .memberCreatedAt(LocalDateTime.now())
                .isSoftDelete(false)
                .build();
        memberRepository.save(existMember2);

        List<Member> list = new ArrayList<>(Arrays.asList(existMember2));

        given(queryMemberRepository.queryFindBirthdayMember(LocalDate.now().plusDays(8).toString())).willReturn(list);

        //when
        List<MemberIdResponse> memberByBirthday = service.findMemberByBirthday(8);


        assertThat(memberByBirthday.isEmpty());

    }
    @Transactional
    @DisplayName("해당 날짜에 생일인 회원 찾기 - 성공")
    @Test
    void testFindMemberByMonthDay(){

        //TODO: 서비스 고치기
        //given
        Long memberId = 1L;
        String monthday = "0426";

        when(queryMemberRepository.queryFindMemberByBirthMonthDay(monthday)).thenReturn(List.of(new MemberIdResponse(memberId)));

        //when
        List<MemberIdResponse> response = service.findMemberIdByLateDay(7);


        //then
        assertThat(response.isEmpty()).isFalse();



    }


}