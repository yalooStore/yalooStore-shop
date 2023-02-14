package com.yaloostore.shop.member.repository.jpa;

import com.yaloostore.shop.member.entity.MemberAddress;
import com.yaloostore.shop.member.repository.common.CommonMemberAddressRepository;
import org.springframework.data.repository.Repository;

public interface JpaMemberAddressRepository extends Repository<MemberAddress, Long>, CommonMemberAddressRepository {

}
