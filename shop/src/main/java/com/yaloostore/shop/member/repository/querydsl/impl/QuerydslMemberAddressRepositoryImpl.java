package com.yaloostore.shop.member.repository.querydsl.impl;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaloostore.shop.member.dto.response.MemberAddressResponse;
import com.yaloostore.shop.member.entity.MemberAddress;
import com.yaloostore.shop.member.entity.QMemberAddress;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuerydslMemberAddressRepositoryImpl implements QuerydslMemberAddressRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * querydsl을 사용해서 사용자 login시에 사용하는 id를 이용해서 회원 배송지 목록을 전부 가져오는 메소드입니다.
     *
     * @param id 사용자가 로그인 시에 사용하는 id 값입니다.
     * @return 해당 사용자가 저장한 배송지 목록을 모두 가져옵니다.
     *
     * @author yeom hwiju
     * */
    @Override
    public List<MemberAddressResponse> queryMemberAddressFindAllByLoginId(String id) {
        QMemberAddress memberAddress = QMemberAddress.memberAddress;

        return queryFactory
                .select(Projections.constructor(MemberAddressResponse.class,
                        memberAddress.memberAddressId,
                        memberAddress.recipientName,
                        memberAddress.recipientPhoneNumber,
                        memberAddress.zipCode,
                        memberAddress.streetAddress,
                        memberAddress.detailedAddress,
                        memberAddress.isDefaultAddress
                ))
                .from(memberAddress)
                .where(memberAddress.member.id.eq(id)
                        .and(memberAddress.isDeletedAddress.isFalse()))
                .orderBy(memberAddress.isDefaultAddress.asc())
                .fetch();
    }


    /**
     * query dsl을 사용하여 해당 memberAddressId를 통해 회원 배송지 정보를 찾아오는 메소드(삭제되지 않은 배송지만 가져올 수 있다.)
     *
     * @param memberAddressId 해당 id값을 이용해서 배송지를 찾는다.
     * @return 회원 배송지 관련 dto 객체를 넘겨준다.
     * */
    @Override
    public Optional<MemberAddressResponse> queryMemberAddressFindByMemberAddressId(Long memberAddressId) {
        QMemberAddress memberAddress = QMemberAddress.memberAddress;
        return Optional.ofNullable(queryFactory.select(Projections.constructor(MemberAddressResponse.class,
                    memberAddress.memberAddressId,
                    memberAddress.recipientName,
                    memberAddress.recipientPhoneNumber,
                    memberAddress.zipCode,
                    memberAddress.streetAddress,
                    memberAddress.detailedAddress,
                    memberAddress.isDefaultAddress
                ))
                .from(memberAddress)
                .where(memberAddress.memberAddressId.eq(memberAddressId)
                        .and(memberAddress.isDeletedAddress.isFalse()))
                .fetchFirst());
    }


    /**
     * 회원 아이디(로그인 아이디)와 회원 배송지 id(기본키)를 사용해서 해당 배송지를 돌려주는 메소드
     *
     * @param memberAddressId 배송지 기본키
     * @param loginId 회원 로그인에 사용하는 Id
     * @return memberAddress DTO 객체
     * */
    @Override
    public Optional<MemberAddressResponse> queryMemberAddressFindByMemberAddressIdAndLoginId(Long memberAddressId, String loginId) {

        QMemberAddress memberAddress = QMemberAddress.memberAddress;

        return Optional.ofNullable(queryFactory.select(Projections.constructor(MemberAddressResponse.class,
                    memberAddress.memberAddressId,
                    memberAddress.recipientName,
                    memberAddress.recipientPhoneNumber,
                    memberAddress.zipCode,
                    memberAddress.streetAddress,
                    memberAddress.detailedAddress,
                    memberAddress.isDefaultAddress
                ))
                .from(memberAddress)
                .where(memberAddress.memberAddressId.eq(memberAddressId)
                        .and(memberAddress.member.id.eq(loginId))).fetchFirst()
        );
    }

    @Override
    public long countMemberAddressByLoginId(String loginId) {

        QMemberAddress memberAddress = QMemberAddress.memberAddress;


        return queryFactory.select(memberAddress.count())
                .from(memberAddress)
                .where(memberAddress.member.id.eq(loginId)
                        .and(memberAddress.isDeletedAddress.isFalse()))
                .fetchFirst();
    }


    /**
     * 해당 로그인 Id와 배송지 주키를 통해서 해당 배송지 정보가 있는지 확인하는 메소드
     *
     * @param memberAddressId 배송지정보 기본키(찾고자하는 배송지 정보 id)
     * @param loginId 해당 배송지를 찾고자하는 회원의 로그인 아이디
     * @return 배송지 정보가 있으면 true 없으면 false를 반환
     * */
    @Override
    public boolean existByLoginIdAndMemberAddressId(Long memberAddressId, String loginId) {

        QMemberAddress memberAddress = QMemberAddress.memberAddress;


        return Optional.ofNullable(queryFactory.select(memberAddress)
                .from(memberAddress)
                .where(memberAddress.memberAddressId.eq(memberAddressId)
                        .and(memberAddress.member.id.eq(loginId))
                        .and(memberAddress.isDeletedAddress.isFalse())).fetchFirst()).isPresent();
    }


}
