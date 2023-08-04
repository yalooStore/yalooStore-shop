package com.yaloostore.shop.member.service.inter;




import com.yaloostore.shop.member.dto.request.MemberCreateRequest;
import com.yaloostore.shop.member.dto.request.MemberUpdateRequest;
import com.yaloostore.shop.member.dto.response.*;
import com.yaloostore.shop.member.dto.transfer.MemberDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    MemberCreateResponse createMember(MemberCreateRequest createRequest);

    MemberUpdateResponse updateMember(Long memberId, MemberUpdateRequest updateRequest);

    List<InactiveMemberResponse> changeInactiveMembers(List<MemberIdResponse> inactiveMembers);

}
