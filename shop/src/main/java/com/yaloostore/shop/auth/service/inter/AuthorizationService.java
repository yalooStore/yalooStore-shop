package com.yaloostore.shop.auth.service.inter;

import com.yaloostore.shop.member.dto.response.AuthorizationMetaResponse;

public interface AuthorizationService {
    AuthorizationMetaResponse authorization(String removePrefixBearer);
}
