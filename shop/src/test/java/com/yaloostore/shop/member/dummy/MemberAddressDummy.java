package com.yaloostore.shop.member.dummy;


import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberAddress;
import lombok.Getter;

@Getter
public class MemberAddressDummy {

    public static MemberAddress dummy(Member member){
        return MemberAddress.builder()
                .member(member)
                .recipientName("김땡땡")
                .recipientPhoneNumber("01099998888")
                .zipCode(1234)
                .streetAddress("test")
                .isDefaultAddress(true)
                .isDeletedAddress(false)
                .build();
    }

    public static MemberAddress dummy2(Member member){
        return MemberAddress.builder()
                .member(member)
                .recipientName("이땡땡")
                .recipientPhoneNumber("01022223333")
                .zipCode(2345)
                .streetAddress("test2")
                .isDefaultAddress(true)
                .isDeletedAddress(false)
                .build();
    }
    public static MemberAddress dummy3(Member member){
        return MemberAddress.builder()
                .member(member)
                .recipientName("이땡땡")
                .recipientPhoneNumber("01022223333")
                .zipCode(2345)
                .streetAddress("test2")
                .isDefaultAddress(false)
                .isDeletedAddress(false)
                .build();
    }
}
