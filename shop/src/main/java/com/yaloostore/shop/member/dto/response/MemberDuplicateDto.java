package com.yaloostore.shop.member.dto.response;


import lombok.Getter;

@Getter
public class MemberDuplicateDto {



    private boolean result;

    public MemberDuplicateDto(boolean result){
        this.result = result;
    }

}
