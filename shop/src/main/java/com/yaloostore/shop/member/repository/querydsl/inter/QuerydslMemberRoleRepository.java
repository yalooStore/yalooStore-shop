package com.yaloostore.shop.member.repository.querydsl.inter;

import java.util.List;

public interface QuerydslMemberRoleRepository {

    List<String> queryFindMemberRoleByLoginId(String loginId);
}
