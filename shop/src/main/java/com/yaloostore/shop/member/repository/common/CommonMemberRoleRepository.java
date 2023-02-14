package com.yaloostore.shop.member.repository.common;

import com.yaloostore.shop.member.entity.MemberRole;

import java.util.Optional;

import static com.yaloostore.shop.member.entity.MemberRole.MemberRolePk;


public interface CommonMemberRoleRepository {

    Optional<MemberRole> findById(MemberRolePk pk);

    MemberRole save(MemberRole memberRole);
}
