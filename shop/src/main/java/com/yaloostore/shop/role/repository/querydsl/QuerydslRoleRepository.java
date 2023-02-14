package com.yaloostore.shop.role.repository.querydsl;


import com.yaloostore.shop.role.repository.dto.response.RoleResponse;

import java.util.Optional;


public interface QuerydslRoleRepository {

    /**
     * Role을 가져오기 위한 Q class와 querydsl를 사용한 메소드
     * @param roleName role type enum 클래스에 저장된 역할 이름으로
     *                 entity에는 Enum type이 필드로 선언되어있어 이를 가져오기 위해선 query dsl 사용이 필수
     * @return roleNo, roleName(원래는 type이지만 이를 string으로 반한)
     * */
    Optional<RoleResponse> queryRoleFindByRoleName(String roleName);

}
