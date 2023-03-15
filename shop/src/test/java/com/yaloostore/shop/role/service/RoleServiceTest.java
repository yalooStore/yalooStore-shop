package com.yaloostore.shop.role.service;

import com.yaloostore.shop.member.dummy.RoleDummy;
import com.yaloostore.shop.role.common.RoleType;
import com.yaloostore.shop.role.entity.Role;
import com.yaloostore.shop.role.repository.dto.response.RoleResponse;
import com.yaloostore.shop.role.repository.basic.RoleCommonRepository;
import com.yaloostore.shop.role.repository.querydsl.QuerydslRoleRepository;
import com.yaloostore.shop.role.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RoleServiceImpl.class)
class RoleServiceTest {

    @Autowired
    RoleService roleService;

    @MockBean
    QuerydslRoleRepository mockQueryRepository;

    @MockBean
    RoleCommonRepository roleCommonRepository;

//    @PersistenceContext
//    EntityManager entityManager;

    private Role user;

    @BeforeEach
    void setUp() {
        user = RoleDummy.roleUserDummy();
    }

    @Test
    void testGetRole() {
        //given
        RoleResponse roleResponse = RoleResponse.fromEntity(user);

        given(mockQueryRepository.queryRoleFindByRoleName("ROLE_USER")).willReturn(Optional.of(roleResponse));

        //when
        Role getRole = roleService.getRole(user.getRoleType().getRoleName());

        //then
        assertThat(getRole.getRoleType().getRoleName()).isEqualTo(RoleType.USER.getRoleName());
    }
}