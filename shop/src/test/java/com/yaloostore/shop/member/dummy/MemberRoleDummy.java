package com.yaloostore.shop.member.dummy;


import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberRole;
import com.yaloostore.shop.role.entity.Role;

public class MemberRoleDummy {

    public static MemberRole userDummy(){
        Long memberId = 1L;
        Long roleId = 1L;

        return MemberRole.builder()
                .memberRolePk(new MemberRole.MemberRolePk(memberId, roleId))
                .member(MemberDummy.dummy())
                .role(RoleDummy.roleUserDummy())
                .build();
    }

    public static MemberRole adminDummy(){
        Long memberId = 1L;
        Long roleId = 1L;

        return MemberRole.builder()
                .memberRolePk(new MemberRole.MemberRolePk(memberId, roleId))
                .member(MemberDummy.dummy())
                .role(RoleDummy.roleAdminDummy())
                .build();
    }


    public static MemberRole dummy(Member member, Role role){

        return MemberRole.builder()
                .memberRolePk(new MemberRole.MemberRolePk(member.getMemberId(), role.getRoleId()))
                .member(member)
                .role(role)
                .build();
    }
}
