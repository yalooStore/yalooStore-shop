package com.yaloostore.shop.member.repository.basicRepository;

import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.dummy.MemberRoleDummy;
import com.yaloostore.shop.member.dummy.MembershipDummy;
import com.yaloostore.shop.member.dummy.RoleDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberRole;
import com.yaloostore.shop.member.entity.Membership;
import com.yaloostore.shop.role.common.RoleType;
import com.yaloostore.shop.role.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.assertj.core.api.Assertions.contentOf;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRoleRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    MemberRoleRepository repository;

    private Member member;
    private Membership membership;
    private Role admin;
    private Role user;
    private MemberRole memberRole;


    @BeforeEach
    void setUp() {
        membership = MembershipDummy.dummy();
        admin = RoleDummy.roleAdminDummy();
        user = RoleDummy.roleUserDummy();
    }

    @DisplayName("멤버 역할 저장 테스트")
    @Test
    void testMemberRoleSave(){
        //given
        Membership persistMembership= entityManager.persist(membership);
        member = MemberDummy.dummy(persistMembership);
        Member persistMember = entityManager.persist(member);

        Role persistAdmin = entityManager.persist(admin);

        memberRole = MemberRoleDummy.dummy(persistMember, persistAdmin);
        MemberRole persistMemberRole = entityManager.persist(memberRole);

        //when
        MemberRole savedAdminMemberRole = repository.save(persistMemberRole);

        //then
        assertThat(savedAdminMemberRole.getRole().getRoleType().getRoleName()).isEqualTo(RoleType.ADMIN.getRoleName());
    }


    @DisplayName("해당 복합키를 사용해서 회원의 역할과 회원 정보를 찾아오는 테스트")
    @Test
    void findByPk() {
        //given
        member = MemberDummy.dummy();
        Member savedMember = entityManager.persist(member);
        Role savedRole = entityManager.persist(user);
        memberRole = MemberRoleDummy.dummy(savedMember, savedRole);

        //when
        repository.save(memberRole);

        Optional<MemberRole> optionalMemberRole = repository.findById(memberRole.getMemberRolePk());

        //then
        assertThat(optionalMemberRole).isPresent();
        assertThat(optionalMemberRole.get().getMember().getName()).isEqualTo(savedMember.getName());
        assertThat(optionalMemberRole.get().getRole().getRoleType().getRoleName()).isEqualTo(savedRole.getRoleType().getRoleName());
    }
}