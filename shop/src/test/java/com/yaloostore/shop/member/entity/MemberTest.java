package com.yaloostore.shop.member.entity;

import com.yaloostore.shop.member.dummy.MemberDummy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {


    @DisplayName("회원 닉네임 변경 테스트")
    @Test
    void testChangeMemberNickname() {
        //given
        String changeNickname = "NewNickname";
        Member member = MemberDummy.dummy();

        //when
        member.changeMemberNickname(changeNickname);

        //then
        assertThat(member.getNickname()).isEqualTo(changeNickname);
    }


    @DisplayName("삭제 테스트 - 회원 객체는 그대로 남아 있으면서 isSoftDelete가 true로 변경")
    @Test
    void softDeleteMember() {
        //given
        Member member = Member.builder()
                .isSoftDelete(false)
                .build();

        //when
        member.softDeleteMember();

        //then
        assertThat(member).isNotNull();
        assertThat(member.isSoftDelete()).isTrue();

    }

    @DisplayName("휴먼 회원 - 휴먼회원 만들기 ")
    @Test
    void makeSleepAccount(){
        Member member = Member.builder()
                .isSleepAccount(false)
                .build();

        member.makeSleepAccount();

        assertThat(member).isNotNull();
        assertThat(member.isSleepAccount()).isTrue();
    }
    @DisplayName("휴먼 회원 - 휴먼회원 만들기 ")
    @Test
    void makeNonSleepAccount(){
        Member member = Member.builder()
                .isSleepAccount(true)
                .build();

        member.makeNonSleepAccount();

        assertThat(member).isNotNull();
        assertThat(member.isSleepAccount()).isFalse();
    }
}