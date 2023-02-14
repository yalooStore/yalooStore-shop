package com.yaloostore.shop.member.dummy;


import com.yaloostore.shop.role.common.RoleType;
import com.yaloostore.shop.role.entity.Role;

public class RoleDummy {

    public static Role roleUserDummy(){
        return Role.builder()
                .roleType(RoleType.USER)
                .build();
    }

    public static Role roleAdminDummy(){
        return Role.builder()
                .roleType(RoleType.ADMIN)
                .build();
    }


}
