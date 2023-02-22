package com.yaloostore.shop.order.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * join 전략을 사용해서 Order class를 상속받은 회원이 아닌 사람의 주문 엔티티입니다.
 *
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "non_member_orders")
@PrimaryKeyJoinColumn(name = "order_id")
public class NonMemberOrder extends Order{


    @Column(name = "non_member_order_address",nullable = false)
    private String nonMemberOrderAddress;

    @Column(name = "non_member_name", nullable = false)
    private String nonMemberName;

    @Column(name = "non_member_phone_number", nullable = false)
    private String nonMemberPhoneNumber;


}
