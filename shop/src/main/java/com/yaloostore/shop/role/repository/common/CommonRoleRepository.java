package com.yaloostore.shop.role.repository.common;

import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.role.entity.Role;

import java.util.Optional;

public interface CommonRoleRepository {

    Optional<Role> findByRoleId(Long roleId);

    Role save(Role role);

}
