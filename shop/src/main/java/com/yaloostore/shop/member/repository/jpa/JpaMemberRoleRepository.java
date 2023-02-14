package com.yaloostore.shop.member.repository.jpa;

import com.yaloostore.shop.member.entity.MemberRole;
import com.yaloostore.shop.member.repository.common.CommonMemberRoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

import static com.yaloostore.shop.member.entity.MemberRole.*;


public interface JpaMemberRoleRepository extends Repository<MemberRole, MemberRolePk>, CommonMemberRoleRepository {
}
