package com.yaloostore.shop.member.service.Impl;

import com.yaloostore.shop.member.dto.response.InactiveMemberResponse;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberRole;
import com.yaloostore.shop.member.repository.basic.MemberRepository;
import com.yaloostore.shop.member.repository.basic.MemberRoleRepository;
import com.yaloostore.shop.member.repository.basic.MembershipHistoryRepository;
import com.yaloostore.shop.member.repository.basic.MembershipRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.member.service.inter.MemberService;
import com.yaloostore.shop.role.repository.basic.RoleCommonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MemberServiceImpl.class)
class MemberServiceTest {

    private MemberService memberService;

    @Mock
    private MemberRoleRepository memberRoleRepository;
    private RoleCommonRepository roleRepository;
    private MembershipRepository membershipRepository;
    private MembershipHistoryRepository membershipHistoryRepository;

    private QuerydslMemberRepository querydslMemberRepository;
    private MemberRepository memberRepository;
    private Member member;
    private Member existMember;
    private Member deletedMember;

    MemberServiceTest() {
    }


    @BeforeEach
    void setUp() {

        memberRoleRepository = Mockito.mock(MemberRoleRepository.class);
        roleRepository = Mockito.mock(RoleCommonRepository.class);
        membershipRepository = Mockito.mock(MembershipRepository.class);
        membershipHistoryRepository= Mockito.mock(MembershipHistoryRepository.class);

        querydslMemberRepository = Mockito.mock(QuerydslMemberRepository.class);
        memberRepository =Mockito.mock(MemberRepository.class);
        memberService = new MemberServiceImpl(memberRoleRepository, roleRepository,membershipRepository
                        ,membershipHistoryRepository, querydslMemberRepository,memberRepository);


    }

    @Test
    void createMember() {
    }

    @Test
    void updateMember() {
    }

    @Test
    void softDeleteMember() {
    }

    @Test
    void changeInactiveMembers(){

        //given
        List<MemberIdResponse> memberIdResponses = Arrays.asList(
                new MemberIdResponse(1L),
                new MemberIdResponse(2L),
                new MemberIdResponse(3L)
        );

        List<Member> members = Arrays.asList(
                Member.builder().emailAddress("1").isSleepAccount(false).build(),
                Member.builder().emailAddress("2").isSleepAccount(false).build(),
                Member.builder().emailAddress("3").isSleepAccount(false).build()
        );

        when(memberRepository.findMemberByMemberId(anyLong())).thenAnswer(
                invocation -> { Long memberId = invocation.getArgument(0);
                    return members.stream().filter(member-> member.getMemberId().equals(memberId)).findFirst();
                });

        List<InactiveMemberResponse> inactiveMemberResponses = memberService.changeInactiveMembers(memberIdResponses);



        //when

    }

}