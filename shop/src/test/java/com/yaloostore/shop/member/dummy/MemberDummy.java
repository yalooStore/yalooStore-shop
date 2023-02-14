package com.yaloostore.shop.member.dummy;


import com.yaloostore.shop.member.common.GenderCode;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.Membership;

import java.time.LocalDateTime;

public class MemberDummy {

    public static Member dummy(){
        String id = "yeomyaloo";
        String name = "yaloo";
        String password = "1234";
        String email = "test@test.com";
        String birthday = "20001111";
        String phoneNumber = "010-0000-0000";

        // memberCreatedAt gradeDummy

        return Member.builder()
                .membership(MembershipDummy.dummy())
                .id(id)
                .nickname(name)
                .name(name)
                .genderCoder(GenderCode.FEMALE)
                .birthday(birthday)
                .password(password)
                .phoneNumber(phoneNumber)
                .emailAddress(email)
                .memberCreatedAt(LocalDateTime.now())
                .build();
    }
    public static Member dummy2(){
        String id = "kimkimkim";
        String name = "kim";
        String password = "4567";
        String email = "kim@test.com";
        String birthday = "19990909";
        String phoneNumber = "01077777777";

        // memberCreatedAt gradeDummy

        return Member.builder()
                .membership(MembershipDummy.dummy())
                .id(id)
                .nickname(name)
                .name(name)
                .genderCoder(GenderCode.MALE)
                .birthday(birthday)
                .password(password)
                .phoneNumber(phoneNumber)
                .emailAddress(email)
                .memberCreatedAt(LocalDateTime.now())
                .build();
    }


    public static Member dummy(Membership membership) {
        String id = "yeomyaloo";
        String name = "yaloo";
        String password = "1234";
        String email = "test@test.com";
        String birthday = "20001111";
        String phoneNumber = "010-0000-0000";
        // memberCreatedAt gradeDummy

        return Member.builder()
                .membership(membership)
                .id(id)
                .nickname(name)
                .name(name)
                .genderCoder(GenderCode.FEMALE)
                .birthday(birthday)
                .password(password)
                .phoneNumber(phoneNumber)
                .emailAddress(email)
                .memberCreatedAt(LocalDateTime.now())
                .build();
    }
}
