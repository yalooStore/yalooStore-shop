package com.yaloostore.shop.member.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yaloostore.shop.member.common.GenderCode;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.Membership;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberCreateRequest {

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z]+[0-9]*$", message ="아이디는 영문과 숫자로만 가능하며 숫자로 시작할 수 없습니다. 올바르게 입력해주세요.")
    private String id;

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,30}$", message = "닉네임은 한글과 영문만 입력해주세요.")
    private String nickname;

    @Size(min = 2, max = 50)
    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    private String name;

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    private String gender;

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Pattern(regexp = "^[0-9]{8}", message = "ex) 1999090 숫자로만 8글자 작성해주세요")
    private String birthday;


    @NotBlank
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 11, max = 30)
    private String phoneNumber;

    @Email
    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 2, max = 100)
    private String emailAddress;


    public Member toEntity(Membership membership){
        return Member.builder()
                .membership(membership)
                .id(id)
                .nickname(nickname)
                .name(name)
                .genderCoder(GenderCode.valueOf(gender))
                .birthday(birthday)
                .phoneNumber(phoneNumber)
                .emailAddress(emailAddress)
                .memberCreatedAt(LocalDateTime.now())
                .isSocialMember(false)
                .isSleepAccount(false)
                .build();

    }




}