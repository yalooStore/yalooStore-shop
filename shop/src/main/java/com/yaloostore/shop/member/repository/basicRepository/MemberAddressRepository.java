package com.yaloostore.shop.member.repository.basicRepository;

import com.yaloostore.shop.member.entity.MemberAddress;
import com.yaloostore.shop.member.repository.common.CommonMemberAddressRepository;
import org.springframework.data.repository.Repository;

public interface MemberAddressRepository extends Repository<MemberAddress, Long>, CommonMemberAddressRepository {

}
