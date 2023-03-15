package com.yaloostore.shop.member.repository.basic;

import com.yaloostore.shop.member.entity.MemberRole;
import com.yaloostore.shop.member.repository.common.CommonMemberRoleRepository;
import org.springframework.data.repository.Repository;

import static com.yaloostore.shop.member.entity.MemberRole.*;


public interface MemberRoleRepository extends Repository<MemberRole, MemberRolePk>, CommonMemberRoleRepository {
}
