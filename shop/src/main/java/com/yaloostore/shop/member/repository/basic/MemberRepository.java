package com.yaloostore.shop.member.repository.basic;


import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.repository.common.CommonMemberRepository;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long>, CommonMemberRepository {

}


