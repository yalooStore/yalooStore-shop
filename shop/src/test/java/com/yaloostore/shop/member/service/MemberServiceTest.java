package com.yaloostore.shop.member.service;

import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.repository.jpa.JpaMemberRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.member.service.Impl.MemberServiceImpl;
import com.yaloostore.shop.member.service.inter.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MemberServiceImpl.class)
class MemberServiceTest {

    @Autowired
    MemberService memberService;



    @MockBean
    JpaMemberRepository mockJpaMemberRepository;

    private Member member;
    private Member existMember;
    private Member deletedMember;



    @BeforeEach
    void setUp() {
        member = MemberDummy.dummy();

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
}