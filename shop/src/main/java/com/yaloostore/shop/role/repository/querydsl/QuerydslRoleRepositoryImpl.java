package com.yaloostore.shop.role.repository.querydsl;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaloostore.shop.role.entity.QRole;
import com.yaloostore.shop.role.repository.dto.response.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuerydslRoleRepositoryImpl implements QuerydslRoleRepository{

    private final JPAQueryFactory factory;


    @Override
    public Optional<RoleResponse> queryRoleFindByRoleName(String roleName) {

        QRole role = QRole.role;

        return Optional.of(
                factory.from(role)
                        .select(Projections.constructor(RoleResponse.class,role.roleId,
                                role.roleType.stringValue())).where(role.roleType.stringValue().eq(roleName))
                        .fetchOne());

    }
}
