package com.yaloostore.shop.role.service.impl;


import com.yaloostore.shop.role.entity.Role;
import com.yaloostore.shop.role.exception.NotFoundRoleTypeException;
import com.yaloostore.shop.role.repository.dto.response.RoleResponse;
import com.yaloostore.shop.role.repository.basic.RoleCommonRepository;
import com.yaloostore.shop.role.repository.querydsl.QuerydslRoleRepository;
import com.yaloostore.shop.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {


    private final RoleCommonRepository roleCommonRepository;
    private final QuerydslRoleRepository querydslRoleRepository;

    @Override
    public Role getRole(String roleName) {
        RoleResponse roleResponse = querydslRoleRepository.queryRoleFindByRoleName(roleName).orElseThrow(NotFoundRoleTypeException::new);

        return Role.toEntity(roleResponse);

    }
}
