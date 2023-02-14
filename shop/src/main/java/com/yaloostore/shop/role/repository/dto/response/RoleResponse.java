package com.yaloostore.shop.role.repository.dto.response;


import com.yaloostore.shop.role.common.RoleType;
import com.yaloostore.shop.role.entity.Role;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {

    private Long roleNo;
    private String roleName;


    public static RoleResponse fromEntity(Role role){
        return RoleResponse.builder()
                .roleNo(role.getRoleId())
                .roleName(role.getRoleType().getRoleName())
                .build();
    }
}
