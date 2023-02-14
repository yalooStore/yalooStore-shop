package com.yaloostore.shop.member.service.inter;

import com.yaloostore.shop.member.dto.response.MemberSoftDeleteResponse;

public interface QueryMemberService {

    MemberSoftDeleteResponse softDeleteMember(Long memberId);

    MemberSoftDeleteResponse softDeleteLoginId(String loginId);
}
