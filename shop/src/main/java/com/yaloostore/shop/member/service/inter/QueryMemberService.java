package com.yaloostore.shop.member.service.inter;

import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginResponse;
import com.yaloostore.shop.member.dto.response.MemberSoftDeleteResponse;
import com.yaloostore.shop.member.entity.Member;

import java.util.List;

public interface QueryMemberService {

    MemberSoftDeleteResponse softDeleteMember(Long memberId);
    MemberSoftDeleteResponse softDeleteLoginId(String loginId);

    Member findByLoginId(String loginId);

    MemberLoginResponse findMemberByLoginId(String loginId);

    List<MemberIdResponse> findMemberByBirthday(String birthday);


}
