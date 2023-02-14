package com.yaloostore.shop.member.repository.common;

import com.yaloostore.shop.member.entity.MemberAddress;

import java.util.Optional;


/**
 * 회원 배송지 등록, 수정, 삭제와 관련된 레포지토리
 * */
public interface CommonMemberAddressRepository {


    /**
     * 회원 배송지 데이터 등록을 위한 메소드
     *
     * @param memberAddress 등록할 회원 배송지 데이터
     * @return 등록된 회원 배송지 데이터
     * */
    MemberAddress save(MemberAddress memberAddress);

    void deleteById(Long memberAddressId);


    Optional<MemberAddress> findById(Long memberAddressId);
}
