package com.yaloostore.shop.member.dto.response;


import com.yaloostore.shop.member.dto.transfer.MemberAddressDto;
import com.yaloostore.shop.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddressResponse {

    private Long memberAddressId;
    private String recipientName;
    private String recipientPhoneNumber;
    private Integer zipCode;
    private String streetAddress;
    private String detailedAddress;
    private boolean isDefaultAddress;



    public static MemberAddressDto toMemberAddressDto(MemberAddressResponse response){
        return new MemberAddressDto(
                response.memberAddressId,
                response.recipientName,
                response.recipientPhoneNumber,
                response.zipCode,
                response.streetAddress,
                response.detailedAddress,
                response.isDefaultAddress);
    }


}
