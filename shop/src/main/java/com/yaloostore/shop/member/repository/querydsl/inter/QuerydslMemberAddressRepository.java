package com.yaloostore.shop.member.repository.querydsl.inter;


import com.yaloostore.shop.member.dto.response.MemberAddressResponse;

import java.util.List;
import java.util.Optional;

/**
 * query dsl을 사용해서 구현한 MemberAddress 레포지토리입니다.
 * */
public interface QuerydslMemberAddressRepository {

    List<MemberAddressResponse> queryMemberAddressFindAllByLoginId(String id);

    Optional<MemberAddressResponse> queryMemberAddressFindByMemberAddressId(Long memberAddressId);

    Optional<MemberAddressResponse> queryMemberAddressFindByMemberAddressIdAndLoginId(Long memberAddressId, String loginId);

    long countMemberAddressByLoginId(String loginId);

    boolean existByLoginIdAndMemberAddressId(Long memberAddressId, String loginId);


}

