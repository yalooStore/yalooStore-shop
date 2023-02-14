package com.yaloostore.shop.role.dummy;

import com.yaloostore.shop.role.common.RoleType;
import com.yaloostore.shop.role.entity.Role;

public class RoleDummy {

    public static Role adminDummy(){

        return Role.builder()
                .roleType(RoleType.ADMIN)
                .build();
    }

    public static Role userDummy(){

        return Role.builder()
                .roleType(RoleType.USER)
                .build();
    }

    public static Role nonMemberDummy(){

        return Role.builder()
                .roleType(RoleType.GUEST)
                .build();
    }


}
