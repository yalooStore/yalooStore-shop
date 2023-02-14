package com.yaloostore.shop.member.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


/**
 * 해당 회원의 등급 기록 엔티티
 * @author yeom hwiju
 * @since: 1.0
 * */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "membership_history")
public class MembershipHistory {

    @Id
    @Column(name = "membership_history_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long membershipHistoryId;

    @Column(name = "update_time",nullable = false)
    private LocalDateTime updateTime;

    @Column(name ="previous_paid_amount", nullable = false)
    private Long previousPaidAmount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "membership_id")
    private Membership membership;


}
