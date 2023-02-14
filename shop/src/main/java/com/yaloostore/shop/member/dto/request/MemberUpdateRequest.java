package com.yaloostore.shop.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberUpdateRequest {

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,15}$", message = "")
    private String nickname;

}