package com.yaloostore.shop.member.dto.transfer;


import com.yaloostore.shop.member.common.GenderCode;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.Membership;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {

    private Long memberId;

    private Membership membership;

    private String id;

    private String nickname;

    private String name;
    private GenderCode genderCoder;


    private String birthday;

    private String password;

    private String phoneNumber;


    private String emailAddress;

    private LocalDateTime memberCreatedAt;


    private Boolean isSocialMember;

    private boolean isSoftDelete;

    private LocalDateTime memberSoftDeletedAt;


    public static MemberDto fromEntity(Member member){

        return new MemberDto(
                member.getMemberId(),
                member.getMembership(),
                member.getId(),
                member.getNickname(),
                member.getName(),
                member.getGenderCoder(),
                member.getBirthday(),
                member.getPassword(),
                member.getPhoneNumber(),
                member.getEmailAddress(),
                member.getMemberCreatedAt(),
                member.getIsSocialMember(),
                member.isSoftDelete(),
                member.getMemberSoftDeletedAt()
        );
    }

    public Member toEntity(Membership membership){
        return Member.builder()
                .id(id)
                .membership(membership)
                .nickname(nickname)
                .name(name)
                .genderCoder(genderCoder)
                .birthday(birthday)
                .password(password)
                .phoneNumber(phoneNumber)
                .emailAddress(emailAddress)
                .memberCreatedAt(memberCreatedAt)
                .isSocialMember(isSocialMember)
                .isSoftDelete(isSoftDelete)
                .memberSoftDeletedAt(memberSoftDeletedAt)
                .build();
    }


}
