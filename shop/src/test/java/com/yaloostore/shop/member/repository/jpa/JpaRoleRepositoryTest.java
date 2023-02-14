package com.yaloostore.shop.member.repository.jpa;


import static org.assertj.core.api.Assertions.assertThat;

import com.yaloostore.shop.member.dummy.RoleDummy;
import com.yaloostore.shop.role.common.RoleType;
import com.yaloostore.shop.role.entity.Role;
import com.yaloostore.shop.role.repository.jpa.JpaRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaRoleRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    JpaRoleRepository repository;


    private Role admin;
    private Role user;

    @BeforeEach
    void setUp() {
        admin = RoleDummy.roleAdminDummy();
        user = RoleDummy.roleUserDummy();
    }

    @DisplayName("Role 저장 테스트")
    @Test
    void testRoleSave(){
        Role savedUserRole = repository.save(user);
        Role savedAdminRole = repository.save(admin);

        assertThat(savedUserRole.getRoleType().getRoleName()).isEqualTo(RoleType.USER.getRoleName());
        assertThat(savedAdminRole.getRoleType().getRoleName()).isEqualTo(RoleType.ADMIN.getRoleName());


    }

    @DisplayName("roleId를 사용해서 해당 회원의 role name을 찾는 테스트")
    @Test
    void findByRoleId() {

        final String ADMIN = RoleType.ADMIN.getRoleName();
        final String USER = RoleType.USER.getRoleName();

        Role savedUserRole = entityManager.persist(user);
        Role savedAdminRole = entityManager.persist(admin);

        Optional<Role> optionalUserRole = repository.findByRoleId(savedUserRole.getRoleId());
        Optional<Role> optionalAdminRole = repository.findByRoleId(savedAdminRole.getRoleId());

        assertThat(optionalUserRole).isPresent();
        assertThat(optionalAdminRole).isPresent();

        assertThat(optionalUserRole.get().getRoleType().getRoleName()).isEqualTo(USER);
        assertThat(optionalAdminRole.get().getRoleType().getRoleName()).isEqualTo(ADMIN);

    }
}