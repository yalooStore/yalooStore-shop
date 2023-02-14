package com.yaloostore.shop.member.entity;

import com.yaloostore.shop.role.entity.Role;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "member_role")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberRole {

    @EmbeddedId
    private MemberRolePk memberRolePk;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id", nullable = false)
    private Role role;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class MemberRolePk implements Serializable {
        @Column(name="member_id", nullable = false)
        private Long memberId;

        @Column(name = "role_id", nullable = false)
        private Long roleId;

    }
}
