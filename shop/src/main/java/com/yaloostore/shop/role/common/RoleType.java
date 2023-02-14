package com.yaloostore.shop.role.common;


import com.yaloostore.shop.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 권한에 대해서 탕비 안정성을 지켜주는 enum 클래스
 */

@Getter
@NoArgsConstructor
public enum RoleType {

    ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), GUEST("NON_MEMBER");
    private String roleName;

    RoleType(String roleName){
        this.roleName = roleName;
    }


    public static RoleType stringToEnum(String s){
        for (RoleType value: RoleType.values()) {
            if(value.getRoleName().equals(s)){
                return value;
            }
        }
        return null;
    }

}
