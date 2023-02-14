package com.yaloostore.shop.member.entity;

import com.yaloostore.shop.member.common.Grade;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


/**
 * 회원 별 등급제에 관한 엔티티 클래스
 * @author yeom hwiju
 * @since: 1.0
 * */
@Entity

@Getter
@Table(name = "membership")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Membership {


    @Id
    @Column(name = "membership_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long membershipId;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @Column(name = "membership_standard_amount", nullable = false)
    private Long membershipStandardAmount;


    @Column(name="membership_point", nullable = false)
    private Long membershipPoint;


    @Builder
    public Membership(Grade grade, Long membershipStandardAmount, Long membershipPoint) {
        this.grade = grade;
        this.membershipStandardAmount = membershipStandardAmount;
        this.membershipPoint = membershipPoint;
    }

    public static Membership createMembership(){
        return Membership.builder()
                .grade(Grade.WHITE)
                .membershipStandardAmount(0L)
                .membershipPoint(1000L)
                .build();
    }

    public void updateMembershipInfo(Grade grade, Long membershipStandardAmount, Long membershipPoint) {
        if(Objects.nonNull(grade)){
            this.grade = grade;
        }

        if(Objects.nonNull(membershipStandardAmount)){
            this.membershipStandardAmount = membershipStandardAmount;
        }

        if(Objects.nonNull(membershipPoint)){
            this.membershipPoint = membershipPoint;
        }
    }


}
