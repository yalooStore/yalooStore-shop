package com.yaloostore.shop.role.repository.jpa;

import com.yaloostore.shop.role.entity.Role;
import com.yaloostore.shop.role.repository.common.CommonRoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface JpaRoleRepository extends Repository<Role, Long>, CommonRoleRepository {

}
