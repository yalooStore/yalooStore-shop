package com.yaloostore.shop.role.entity;

import com.yaloostore.shop.role.common.RoleType;
import com.yaloostore.shop.role.common.converter.RoleTypeConverter;
import com.yaloostore.shop.role.repository.dto.response.RoleResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="roles")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Role {

    @Id
    @Column(name ="role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;


    @Convert(converter = RoleTypeConverter.class)
    @Column(name = "role_type", unique = true, nullable = false)
    private RoleType roleType;


    public static Role toEntity(RoleResponse response){
        return Role.builder()
                .roleId(response.getRoleNo())
                .roleType(RoleType.stringToEnum(response.getRoleName()))
                .build();
    }

    
}
