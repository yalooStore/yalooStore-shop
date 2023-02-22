package com.yaloostore.shop.order.entity;


import ch.qos.logback.core.net.server.Client;
import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberAddress;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * join 전략을 사용해서 Order class를 상속받은 회원 주문 엔티티입니다.
 *
 */
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member_orders")
@PrimaryKeyJoinColumn(name = "order_id")
public class MemberOrder extends Order{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_address_id", nullable = false)
    private MemberAddress memberAddress;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    /**
     * hiddenOn - 주문을 숨김처리하는 메소드
     * hiddenOff() - 숨긴 처리된 주문을 해제하는 메소드
     * */
    public void hiddenOn(){
        if(this.isHidden){
            throw new ClientException(
                    ErrorCode.BAD_REQUEST,
                    "Order already hidden with id: "+ getOrderId()
            );
        }
        this.isHidden = true;
    }
    public void hiddenOff(){
        if(this.isHidden){
            throw new ClientException(
                    ErrorCode.BAD_REQUEST,
                    "Order already showing with id: "+ getOrderId()
            );
        }
        this.isHidden = false;
    }

}
