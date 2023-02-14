package com.yaloostore.shop.role.repository;


import com.yaloostore.shop.role.common.RoleType;
import com.yaloostore.shop.role.entity.Role;
import com.yaloostore.shop.role.repository.dto.response.RoleResponse;
import com.yaloostore.shop.role.dummy.RoleDummy;
import com.yaloostore.shop.role.repository.querydsl.QuerydslRoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class QuerydslRoleRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;


    @Autowired
    QuerydslRoleRepository querydslRoleRepository;

    private Role user;
    private Role admin;
    private Role guest;


    @BeforeEach
    void setUp(){
        user = RoleDummy.userDummy();
        admin = RoleDummy.adminDummy();
        guest = RoleDummy.nonMemberDummy();
    }

    @DisplayName("타입 안정성을 위해서 role name을 enum으로 지정해주었을 때 한번에 roleNo와 roleName을 가져오는 querydsl method 테스트")
    @Test
    void testQueryRoleFindByRoleName(){
        //given
        entityManager.persist(user);
        entityManager.persist(admin);
        entityManager.persist(guest);

        //when
        Optional<RoleResponse> roleUser = querydslRoleRepository.queryRoleFindByRoleName(RoleType.USER.getRoleName());
        Optional<RoleResponse> roleAdmin = querydslRoleRepository.queryRoleFindByRoleName(RoleType.ADMIN.getRoleName());
        Optional<RoleResponse> nonMember = querydslRoleRepository.queryRoleFindByRoleName(RoleType.GUEST.getRoleName());

        //then
        assertThat(roleUser.isPresent());
        assertThat(roleAdmin.isPresent());
        assertThat(nonMember.isPresent());

        assertThat(roleUser.get().getRoleName()).isEqualTo(RoleType.USER.getRoleName());
        assertThat(roleAdmin.get().getRoleName()).isEqualTo(RoleType.ADMIN.getRoleName());
        assertThat(nonMember.get().getRoleName()).isEqualTo(RoleType.GUEST.getRoleName());

    }

}