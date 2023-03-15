package com.yaloostore.shop.role.repository.basic;

import com.yaloostore.shop.role.entity.Role;
import com.yaloostore.shop.role.repository.common.CommonRoleRepository;
import org.springframework.data.repository.Repository;

public interface RoleCommonRepository extends Repository<Role, Long>, CommonRoleRepository {

}
