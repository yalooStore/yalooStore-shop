package com.yaloostore.shop.member.entity;


import com.yaloostore.shop.member.common.GenderCode;
import com.yaloostore.shop.member.common.converter.GenderCodeConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


/**
 * 회원관련 엔티티 클래스
 * @author yeom hwiju
 * @since: 1.0
 * */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "members")
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberId;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Column(name ="id", nullable = false, columnDefinition = "varchar(20)", unique = true)
    private String id;

    @Column(unique = true, nullable = false, columnDefinition = "varchar(30)")
    private String nickname;

    @Column(nullable = false)
    private String name;

    @Column(name = "gender_code",nullable = false)
    @Convert(converter = GenderCodeConverter.class)
    private GenderCode genderCoder;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String emailAddress;

    @Column(name = "member_created_at")
    private LocalDateTime memberCreatedAt;


    @Column(name = "is_social_member")
    private Boolean isSocialMember;

    @Column(name = "is_soft_delete", nullable = false, columnDefinition = "boolean default false")
    private boolean isSoftDelete;

    @Column(name = "member_soft_deleted_at")
    private LocalDateTime memberSoftDeletedAt;

    @Column(name = "is_sleep_account", columnDefinition = "boolean default false")
    private boolean isSleepAccount;

    public void changeMemberNickname(String newNickname){
        this.nickname = newNickname;
    }


    /**
     * Member Entity를 soft delete(논리 삭제)하기 위한 기능
     */
    public void softDeleteMember(){
        String deleteId = "withdraw member - @" + this.memberId;
        this.isSoftDelete = true;
        this.memberSoftDeletedAt = LocalDateTime.now();
        this.name = deleteId;
        this.nickname = deleteId;
        this.birthday = "";
        this.phoneNumber = deleteId;
        this.password = "";
    }

    /**
     * 해당 회원을 휴먼회원으로 만듭니다.
     * */
    public void makeSleepAccount(){
        this.isSleepAccount = true;
    }

    /**
     * 해당 휴먼을 다시 활성 회원으로 만듭니다.
     * */
    public void makeNonSleepAccount(){
        this.isSleepAccount = false;
    }



}
