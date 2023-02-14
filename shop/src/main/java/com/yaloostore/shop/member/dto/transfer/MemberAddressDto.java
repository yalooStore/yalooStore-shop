package com.yaloostore.shop.member.dto.transfer;


import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberAddress;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberAddressDto {

    private Long memberAddressId;
    private String recipientName;
    private String recipientPhoneNumber;
    private Integer zipCode;
    private String streetAddress;
    private String detailedAddress;
    private boolean isDefaultAddress;


    /**
     * MemberAddress entity를 dto로 변환시켜주는 메소드입니다.
     * */
    public static MemberAddressDto fromEntity(MemberAddress memberAddress){

        return new MemberAddressDto(
                memberAddress.getMemberAddressId(),
                memberAddress.getRecipientName(),
                memberAddress.getRecipientPhoneNumber(),
                memberAddress.getZipCode(),
                memberAddress.getStreetAddress(),
                memberAddress.getDetailedAddress(),
                memberAddress.isDefaultAddress()
        );
    }

    /**
     * MemberAddress DTO를 MemberAddress Entity로 변환시켜주는 메소드입니다.
     * */
    public MemberAddress toEntity(Member member){
        return MemberAddress.builder()
                .member(member)
                .recipientName(recipientName)
                .recipientPhoneNumber(recipientPhoneNumber)
                .zipCode(zipCode)
                .streetAddress(streetAddress)
                .detailedAddress(detailedAddress)
                .isDefaultAddress(isDefaultAddress)
                .isDeletedAddress(false)
                .build();
    }
}
