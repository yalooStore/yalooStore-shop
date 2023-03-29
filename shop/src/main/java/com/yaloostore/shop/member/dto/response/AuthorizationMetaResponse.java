package com.yaloostore.shop.member.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationMetaResponse {

    private String name;
    private List<String> authorities;

}
