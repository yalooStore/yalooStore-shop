package com.yaloostore.shop.member.service.inter;




import com.yaloostore.shop.member.dto.request.MemberCreateRequest;
import com.yaloostore.shop.member.dto.request.MemberUpdateRequest;
import com.yaloostore.shop.member.dto.response.MemberCreateResponse;
import com.yaloostore.shop.member.dto.response.MemberSoftDeleteResponse;
import com.yaloostore.shop.member.dto.response.MemberUpdateResponse;

import java.util.Optional;

public interface MemberService {

    MemberCreateResponse createMember(MemberCreateRequest createRequest);

    MemberUpdateResponse updateMember(Long memberId, MemberUpdateRequest updateRequest);


}
