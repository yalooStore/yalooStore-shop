package com.yaloostore.shop.member.repository.jpa;


import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.repository.common.CommonMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface JpaMemberRepository extends Repository<Member, Long>, CommonMemberRepository {

}


